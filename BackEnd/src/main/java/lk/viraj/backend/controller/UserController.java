package lk.viraj.backend.controller;

import jakarta.validation.Valid;
import lk.viraj.backend.dto.AuthDTO;
import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.service.UserService;
import lk.viraj.backend.util.JwtUtil;
import lk.viraj.backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sysuser")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(path = "/retrieve", params = "email")
    @PreAuthorize("hasAuthority('USER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getUserOrAdmin(@RequestParam("email") String email) {
        return searchSysUser(email);
    }

    @GetMapping(path = "/retrieve/user", params = "email")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> getUser(@RequestParam("email") String email) {
        return searchSysUser(email);
    }

    @GetMapping(path = "/retrieve/admin", params = "email")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAdmin(@RequestParam("email") String email) {
        return searchSysUser(email);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signUpUser(@Valid @RequestBody UserDTO userDTO) {
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

    private ResponseEntity<ResponseDTO> searchSysUser(String email){
        UserDTO userDTO = userService.searchUser(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Account Found!", userDTO));
    }

}
