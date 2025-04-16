package lk.viraj.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletResponse;
import lk.viraj.backend.dto.*;
import lk.viraj.backend.service.MailService;
import lk.viraj.backend.service.impl.UserServiceImpl;
import lk.viraj.backend.util.JwtUtil;
import lk.viraj.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/auth")
@CrossOrigin
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

    @Autowired
    private MailService mailService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    //constructor injection
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }

        UserDTO loadedUser = userService.loadUserDetailsByUsername(loginDTO.getEmail());
        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        String token = jwtUtil.generateToken(loadedUser);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setToken(token);
        authDTO.setUser(loadedUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Success", authDTO));
    }

    @GetMapping("/oauth2Login")
    public RedirectView oauth2Login(@RequestParam("code") String code) throws IOException {
        String accessTokenGoogle = getOauthAccessTokenGoogle(code);
        JsonObject profileDetailsGoogle = getProfileDetailsGoogle(accessTokenGoogle);

        String googleId = profileDetailsGoogle.get("id").getAsString();
        String email = profileDetailsGoogle.get("email").getAsString();
        String name = profileDetailsGoogle.get("name").getAsString();
        String picture = profileDetailsGoogle.get("picture").getAsString();

        UserDTO user = userService.searchUser(email);
        boolean isNew = false;
        if (user == null) {
            isNew = true;
            user = new UserDTO();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(googleId);
            user.setImagePath(picture);
            user.setGoogleId(googleId);
            user.setGoogleUser(true);
            userService.saveUser(user);  // Save new user
        }

        ProfileDTO profileDTO = userService.convertToProfileDTO(user);

        // Generate a JWT token for your system (for session management)
        String jwtToken = jwtUtil.generateToken(user);

        String redirectUrl = UriComponentsBuilder
                .fromUriString("https://digitalartgallery-2025.web.app/index.html")
                .queryParam("jwtToken", jwtToken)
                .queryParam("accessToken", accessTokenGoogle)
                .build()
                .toUriString();

        if (!isNew) {
            mailService.sendLoggedInEmail(name, email, "Login Alert!");
        } else {
            mailService.sendRegisteredEmail(name, email, "Registered Successfully!");
        }

        return new RedirectView(redirectUrl);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<ResponseDTO> sendOtp(@RequestParam String email) {
        mailService.sendOptEmail(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Opt Sent Success", null));
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<ResponseDTO> verifyOtp(@RequestParam int otp, @RequestParam String pw, @RequestParam String email) {
        boolean isValid = mailService.verifyOtp(otp);
        if (isValid){
            UserDTO userDTO = userService.searchUser(email);
            if (userDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encode = passwordEncoder.encode(pw);
            userDTO.setPassword(encode);
            userService.updateUser(userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", null));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO(VarList.Unauthorized, "Invalid OTP", null));
    }


    private String getOauthAccessTokenGoogle(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("redirect_uri", "http://localhost:8080/api/v1/auth/oauth2Login");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email");
        params.add("scope", "openid");
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        String url = "https://oauth2.googleapis.com/token";
        String response = restTemplate.postForObject(url, requestEntity, String.class);
        assert response != null;
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }

    private JsonObject getProfileDetailsGoogle(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return new Gson().fromJson(response.getBody(), JsonObject.class);
    }

}

