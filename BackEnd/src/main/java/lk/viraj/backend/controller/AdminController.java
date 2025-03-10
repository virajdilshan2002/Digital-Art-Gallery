package lk.viraj.backend.controller;

import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.service.UserService;
import lk.viraj.backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<ResponseDTO> getProfile(@RequestHeader("Authorization") String authorization) {
        UserDTO userByToken = userService.getUserByToken(authorization.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "profile data retrieved success", userByToken));
    }
}
