package lk.viraj.backend.controller;

import lk.viraj.backend.dto.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<ResponseDTO> save(@RequestHeader("Authorization") String authorization, @RequestPart("artName") String name, @RequestPart("artPhoto") MultipartFile file, @RequestPart("artDescription") String description, @RequestPart("artPrice") String price, @RequestPart("artQty") String qty) {

        System.out.println(file.getOriginalFilename());

        return ResponseEntity.ok(new ResponseDTO(200, "Data received successfully", null));
    }
}
