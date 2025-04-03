package lk.viraj.backend.controller;

import jakarta.validation.Valid;
import lk.viraj.backend.dto.AuthDTO;
import lk.viraj.backend.dto.ProfileDTO;
import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.dto.other.ImageUploadDTO;
import lk.viraj.backend.service.FileStorageService;
import lk.viraj.backend.service.UserService;
import lk.viraj.backend.util.JwtUtil;
import lk.viraj.backend.util.VarList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final FileStorageService fileStorageService;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil, FileStorageService fileStorageService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping(path = "/retrieve")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ResponseDTO> retrieveUser(@RequestHeader("Authorization") String authorization) {
        System.out.println("dfghjkl;");
        String role = userService.getUserRoleByToken(authorization.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "retrieved success", role));
    }

    @GetMapping(path = "/profile")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ResponseDTO> getProfile(@RequestHeader("Authorization") String authorization) {
        UserDTO user = userService.getUserByToken(authorization.substring(7));
        ProfileDTO profileDTO = userService.convertToProfileDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "profile data retrieved success", profileDTO));
    }

    @PutMapping(path = "/profile/saveImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ResponseDTO> saveProfileImage(@RequestHeader("Authorization") String authorization, @ModelAttribute() ImageUploadDTO imageUploadDTO) {

            UserDTO user = userService.getUserByToken(authorization.substring(7));

            if (user.getImagePath() != null) {
                //remove the old image
                fileStorageService.deleteImage(user.getImagePath());
            }

            String savedPath = fileStorageService.saveUserProfileImage(imageUploadDTO.getImageFile());

            user.setImagePath(savedPath);

            userService.updateUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", savedPath));
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
                    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

}
