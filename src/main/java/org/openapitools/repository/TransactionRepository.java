package org.openapitools.repository;

import org.openapitools.entity.TransactionEntity;
import org.openapitools.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    
    List<TransactionEntity> findByType(TransactionType type);
    
    @Query("SELECT SUM(t.amount) FROM TransactionEntity t WHERE t.type = ?1")
    Double sumAmountByType(TransactionType type);
}