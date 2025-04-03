package lk.viraj.backend.service;

import lk.viraj.backend.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    int saveCategory(CategoryDTO categoryDTO);
    CategoryDTO searchCategory(String name);

    CategoryDTO searchById(String id);

    List<CategoryDTO> getAllCategory();
}
