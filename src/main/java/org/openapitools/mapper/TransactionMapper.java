package org.openapitools.mapper;

import org.openapitools.entity.CategoryEntity;
import org.openapitools.entity.TransactionEntity;
import org.openapitools.model.Category;
import org.openapitools.model.Transaction;
import org.openapitools.model.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    
    private final CategoryMapper categoryMapper;
    
    @Autowired
    public TransactionMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    
    public Transaction toModel(TransactionEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Transaction model = new Transaction();
        model.setId(entity.getId());
        model.setDescription(entity.getDescription());
        model.setAmount(entity.getAmount());
        model.setDate(entity.getDate());
        model.setType(entity.getType());
        
        if (entity.getCategory() != null) {
            model.setCategory(categoryMapper.toModel(entity.getCategory()));
        }
        
        return model;
    }
    
    public TransactionEntity toEntity(Transaction model) {
        if (model == null) {
            return null;
        }
        
        TransactionEntity entity = new TransactionEntity();
        entity.setId(model.getId());
        entity.setDescription(model.getDescription());
        entity.setAmount(model.getAmount());
        entity.setDate(model.getDate());
        entity.setType(model.getType());
        
        if (model.getCategory() != null) {
            entity.setCategory(categoryMapper.toEntity(model.getCategory()));
        }
        
        return entity;
    }
    
    public TransactionEntity createEntityFromRequest(TransactionRequest request, CategoryEntity category) {
        if (request == null) {
            return null;
        }
        
        TransactionEntity entity = new TransactionEntity();
        entity.setDescription(request.getDescription());
        entity.setAmount(request.getAmount());
        entity.setDate(request.getDate());
        entity.setType(request.getType());
        entity.setCategory(category);
        
        return entity;
    }
    
    public void updateEntityFromRequest(TransactionRequest request, TransactionEntity entity, CategoryEntity category) {
        if (request == null || entity == null) {
            return;
        }
        
        entity.setDescription(request.getDescription());
        entity.setAmount(request.getAmount());
        entity.setDate(request.getDate());
        entity.setType(request.getType());
        entity.setCategory(category);
    }
}