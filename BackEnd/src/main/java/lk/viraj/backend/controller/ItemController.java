package lk.viraj.backend.controller;

import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;
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

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ResponseDTO> save(@RequestHeader("Authorization") String authorizationg,
                                            @ModelAttribute() ItemFormDataDTO requestDTO) {

        //Access data in form
        System.out.println(requestDTO);

        return ResponseEntity.ok(new ResponseDTO(200, "Data received successfully", null));
    }
}
