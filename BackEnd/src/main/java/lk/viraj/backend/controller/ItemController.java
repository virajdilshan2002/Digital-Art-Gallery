package lk.viraj.backend.controller;

import lk.viraj.backend.dto.CategoryDTO;
import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;
import lk.viraj.backend.service.CategoryService;
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

import java.util.List;

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
    private final CategoryService categoryService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final FileStorageService fileStorageService;

    public ItemController(JwtUtil jwtUtil, ItemService itemService, CategoryService categoryService, UserService userService, FileStorageService fileStorageService) {
        this.jwtUtil = jwtUtil;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ResponseDTO> save(@RequestHeader("Authorization") String authorization,
                                            @ModelAttribute() ItemFormDataDTO itemFormDataDTO) {

        //get userDTO using token
        UserDTO userDTO = userService.getUserByToken(authorization.substring(7));

        //get category using category name
        CategoryDTO categoryDTO = categoryService.searchById(itemFormDataDTO.getCategoryId());

        //save image on DIRECTORY
        String path = fileStorageService.saveItemImage(itemFormDataDTO.getItemImage());

        //convert ItemFormDataDTO to ItemDTO
        ItemDTO itemDTO = itemService.convertToItemDTO(itemFormDataDTO, userDTO, categoryDTO, path);

        //save item
        int status = itemService.saveItem(itemDTO);

        return ResponseEntity.ok(new ResponseDTO(200, "Data received successfully", status));
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(200, "Data received successfully", itemService.getAllItems()));
    }

    @GetMapping(path = "/ownedItemsToUser")
    public ResponseEntity<ResponseDTO> ownedItemsToUser(@RequestHeader("Authorization") String authorization) {
        UserDTO userDTO = userService.getUserByToken(authorization.substring(7));

        List<ItemDTO> itemDTOS = itemService.getOwnedItemsByUser(userDTO);

        return ResponseEntity.ok(new ResponseDTO(200, "Data received successfully", itemDTOS));
    }

    @DeleteMapping(path = "/delete", params = "iid")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseDTO> delete(@RequestParam("iid") String itemId) {
        boolean isDeleted = itemService.deleteItemById(itemId);
        return ResponseEntity.ok(new ResponseDTO(200, "Item Deleted successfully", isDeleted));
    }
}
