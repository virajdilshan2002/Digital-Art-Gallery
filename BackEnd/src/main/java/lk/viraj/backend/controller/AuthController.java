package lk.viraj.backend.controller;

import lk.viraj.backend.dto.AuthDTO;
import lk.viraj.backend.dto.LoginDTO;
import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.dto.other.TokenRequestDTO;
import lk.viraj.backend.service.impl.UserServiceImpl;
import lk.viraj.backend.util.JwtUtil;
import lk.viraj.backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;


import java.util.Collections;

@RestController
@RequestMapping(path = "api/v1/auth")
@CrossOrigin
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

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


    @PostMapping("/google")
    public ResponseEntity<ResponseDTO> googleLogin(@RequestBody TokenRequestDTO tokenRequest) {
        /*try {
            // Verify Google token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(tokenRequest.getToken());
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO(VarList.Unauthorized, "Invalid Google Token", null));
            }

            Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            // Check if user exists, otherwise create a new user
            UserDTO user = userService.loadUserDetailsByUsername(email);
            if (user == null) {
                user = new UserDTO();
                user.setEmail(email);
                user.setName(name);
                user.setImagePath(pictureUrl);
                userService.saveUser(user); // Save new user
            }

            // Generate JWT token
            String token = jwtUtil.generateToken(user);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Token Generation Failed", null));
            }

            // Create AuthDTO response
            AuthDTO authDTO = new AuthDTO();
            authDTO.setToken(token);
            authDTO.setUser(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Login Successful", authDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Google Authentication Failed", e.getMessage()));
        }*/
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Login Successful", null));
    }

}

