package org.openapitools.service;

import org.openapitools.entity.CategoryEntity;
import org.openapitools.entity.TransactionEntity;
import org.openapitools.mapper.TransactionMapper;
import org.openapitools.model.Transaction;
import org.openapitools.model.TransactionRequest;
import org.openapitools.model.TransactionType;
import org.openapitools.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final TransactionMapper transactionMapper;
    
    @Autowired
    public TransactionService(
            TransactionRepository transactionRepository,
            CategoryService categoryService,
            TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
        this.transactionMapper = transactionMapper;
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toModel)
                .collect(Collectors.toList());
    }
    
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toModel);
    }
    
    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Transaction request cannot be null");
        }
        
        // Validate required fields
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (request.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (request.getDate() == null) {
            throw new IllegalArgumentException("Date is required");
        }
        if (request.getType() == null) {
            throw new IllegalArgumentException("Transaction type is required");
        }
        
        // Get category if provided
        CategoryEntity category = null;
        if (request.getCategoryId() != null) {
            category = categoryService.findEntityById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + request.getCategoryId()));
        }
        
        // Create transaction entity
        TransactionEntity entity = transactionMapper.createEntityFromRequest(request, category);
        TransactionEntity saved = transactionRepository.save(entity);
        
        return transactionMapper.toModel(saved);
    }
    
    @Transactional
    public Optional<Transaction> updateTransaction(Long id, TransactionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Transaction request cannot be null");
        }
        
        // Validate required fields
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (request.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (request.getDate() == null) {
            throw new IllegalArgumentException("Date is required");
        }
        if (request.getType() == null) {
            throw new IllegalArgumentException("Transaction type is required");
        }
        
        Optional<TransactionEntity> entityOpt = transactionRepository.findById(id);
        if (entityOpt.isEmpty()) {
            return Optional.empty();
        }
        
        // Get category if provided
        CategoryEntity category = null;
        if (request.getCategoryId() != null) {
            category = categoryService.findEntityById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + request.getCategoryId()));
        }
        
        TransactionEntity entity = entityOpt.get();
        transactionMapper.updateEntityFromRequest(request, entity, category);
        TransactionEntity updated = transactionRepository.save(entity);
        
        return Optional.of(transactionMapper.toModel(updated));
    }
    
    @Transactional
    public boolean deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            return false;
        }
        transactionRepository.deleteById(id);
        return true;
    }
    
    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type).stream()
                .map(transactionMapper::toModel)
                .collect(Collectors.toList());
    }
    
    public Double getTotalByType(TransactionType type) {
        Double sum = transactionRepository.sumAmountByType(type);
        return sum != null ? sum : 0.0;
    }
    
    public List<Transaction> getRecentTransactions(int limit) {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toModel)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}