package lk.viraj.backend.controller;

import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;
import lk.viraj.backend.service.FileStorageService;
import lk.viraj.backend.service.ItemService;
import lk.viraj.backend.service.UserService;
import lk.viraj.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.annotation.MultipartConfig;

@RestController
@RequestMapping(path = "api/v1/item")
@MultipartConfig(fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024)
@CrossOrigin
public class ItemController {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final ItemService itemService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final FileStorageService fileStorageService;

    public ItemController(JwtUtil jwtUtil, ItemService itemService, UserService userService, FileStorageService fileStorageService) {
        this.jwtUtil = jwtUtil;
        this.itemService = itemService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ResponseDTO> save(@RequestHeader("Authorization") String authorizationg,
                                            @ModelAttribute() ItemFormDataDTO itemFormDataDTO) {

        //get username in token
        String username = jwtUtil.getUsernameFromToken(authorizationg.substring(7));
        //get userDTO using username
        UserDTO userDTO = userService.searchUser(username);

        //save image on DIRECTORY
        String path = fileStorageService.saveItemImage(itemFormDataDTO.getImage());


        return ResponseEntity.ok(new ResponseDTO(200, "Data received successfully", path));
    }
}
