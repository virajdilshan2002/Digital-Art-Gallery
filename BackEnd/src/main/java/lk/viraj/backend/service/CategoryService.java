package lk.viraj.backend.service;

import lk.viraj.backend.dto.CategoryDTO;

public interface CategoryService {
    int saveCategory(CategoryDTO categoryDTO);
    CategoryDTO searchCategory(String name);
}
