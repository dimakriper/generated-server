package org.openapitools.mapper;

import org.openapitools.entity.CategoryEntity;
import org.openapitools.model.Category;
import org.openapitools.model.CategoryRequest;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    
    public Category toModel(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Category model = new Category();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setColor(entity.getColor());
        
        return model;
    }
    
    public CategoryEntity toEntity(Category model) {
        if (model == null) {
            return null;
        }
        
        CategoryEntity entity = new CategoryEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setColor(model.getColor());
        
        return entity;
    }
    
    public CategoryEntity createEntityFromRequest(CategoryRequest request) {
        if (request == null) {
            return null;
        }
        
        CategoryEntity entity = new CategoryEntity();
        entity.setName(request.getName());
        entity.setColor(request.getColor());
        
        return entity;
    }
    
    public void updateEntityFromRequest(CategoryRequest request, CategoryEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        
        entity.setName(request.getName());
        entity.setColor(request.getColor());
    }
}