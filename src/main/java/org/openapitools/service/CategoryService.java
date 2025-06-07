package org.openapitools.service;

import org.openapitools.entity.CategoryEntity;
import org.openapitools.mapper.CategoryMapper;
import org.openapitools.model.Category;
import org.openapitools.model.CategoryRequest;
import org.openapitools.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toModel)
                .collect(Collectors.toList());
    }
    
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toModel);
    }
    
    public Optional<CategoryEntity> findEntityById(Long id) {
        return categoryRepository.findById(id);
    }
    
    @Transactional
    public Category createCategory(CategoryRequest request) {
        CategoryEntity entity = categoryMapper.createEntityFromRequest(request);
        CategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.toModel(saved);
    }
    
    @Transactional
    public Optional<Category> updateCategory(Long id, CategoryRequest request) {
        Optional<CategoryEntity> entityOpt = categoryRepository.findById(id);
        
        if (entityOpt.isEmpty()) {
            return Optional.empty();
        }
        
        CategoryEntity entity = entityOpt.get();
        categoryMapper.updateEntityFromRequest(request, entity);
        CategoryEntity updated = categoryRepository.save(entity);
        return Optional.of(categoryMapper.toModel(updated));
    }
    
    @Transactional
    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return false;
        }
        categoryRepository.deleteById(id);
        return true;
    }
}