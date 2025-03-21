package lk.viraj.backend.service.impl;

import jakarta.transaction.Transactional;
import lk.viraj.backend.dto.CategoryDTO;
import lk.viraj.backend.entity.Category;
import lk.viraj.backend.repo.CategoryRepository;
import lk.viraj.backend.service.CategoryService;
import lk.viraj.backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
        return VarList.Created;
    }

    @Override
    public CategoryDTO searchCategory(String id) {
        Category category = categoryRepository.getReferenceById(id);
        return modelMapper.map(category, CategoryDTO.class);
    }
}
