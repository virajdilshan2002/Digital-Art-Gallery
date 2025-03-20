package lk.viraj.backend.controller;

import lk.viraj.backend.dto.CategoryDTO;
import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.service.CategoryService;
import lk.viraj.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        int status = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Category Saved!", categoryDTO));
    }
}
