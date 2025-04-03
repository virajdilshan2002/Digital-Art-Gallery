package lk.viraj.backend.service.impl;

import jakarta.transaction.Transactional;
import lk.viraj.backend.dto.CategoryDTO;
import lk.viraj.backend.entity.Category;
import lk.viraj.backend.repo.CategoryRepository;
import lk.viraj.backend.service.CategoryService;
import lk.viraj.backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public CategoryDTO searchCategory(String name) {
        Category category = categoryRepository.findByName(name);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Transactional
    @Override
    public CategoryDTO searchById(String id) {
        Category category = categoryRepository.getReferenceById(id);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryDTO>>() {
        }.getType());
    }
}
