package lk.viraj.backend.controller;

import jakarta.validation.Valid;
import lk.viraj.backend.dto.AuthDTO;
import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.service.UserService;
import lk.viraj.backend.util.JwtUtil;
import lk.viraj.backend.util.VarList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(path = "/retrieve")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ResponseDTO> verifyUser(@RequestHeader("Authorization") String authorization) {
        try {
            String role = userService.getUserRoleByToken(authorization.substring(7));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "retrieved success", role));
        } catch (Exception e) {
            log.error("Token processing error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Token validation failed", null));
        }
    }

    @GetMapping(path = "/profile")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> getProfile(@RequestHeader("Authorization") String authorization) {
        UserDTO userByToken = userService.getUserByToken(authorization.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "profile data retrieved success", userByToken));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setUser(userDTO);
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

}
