package org.openapitools.dao;

import org.openapitools.model.Transaction;
import org.openapitools.model.Category;
import org.openapitools.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Data Access Object for Transaction entities
 */
public class TransactionDAO {
    private static final Map<Long, Transaction> transactions = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong();
    
    static {
        // Add some sample data
        Category food = CategoryDAO.findById(1L);
        Category transport = CategoryDAO.findById(2L);
        
        addTransaction(new Transaction()
            .id(idCounter.incrementAndGet())
            .description("Grocery shopping")
            .amount(50.75)
            .date(LocalDate.now())
            .category(food)
            .type(TransactionType.EXPENSE));
            
        addTransaction(new Transaction()
            .id(idCounter.incrementAndGet())
            .description("Bus ticket")
            .amount(2.50)
            .date(LocalDate.now())
            .category(transport)
            .type(TransactionType.EXPENSE));
            
        addTransaction(new Transaction()
            .id(idCounter.incrementAndGet())
            .description("Salary")
            .amount(2000.00)
            .date(LocalDate.now())
            .type(TransactionType.INCOME));
    }
    
    public static List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }
    
    public static Transaction findById(Long id) {
        return transactions.get(id);
    }
    
    public static Transaction addTransaction(Transaction transaction) {
        if (transaction.getId() == null) {
            transaction.setId(idCounter.incrementAndGet());
        }
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }
    
    public static boolean updateTransaction(Transaction transaction) {
        if (transaction.getId() == null || !transactions.containsKey(transaction.getId())) {
            return false;
        }
        transactions.put(transaction.getId(), transaction);
        return true;
    }
    
    public static boolean deleteTransaction(Long id) {
        return transactions.remove(id) != null;
    }
    
    public static List<Transaction> getTransactionsByType(TransactionType type) {
        return transactions.values().stream()
            .filter(t -> t.getType() == type)
            .collect(Collectors.toList());
    }
    
    public static Double getTotalByType(TransactionType type) {
        return transactions.values().stream()
            .filter(t -> t.getType() == type)
            .mapToDouble(Transaction::getAmount)
            .sum();
    }
    
    public static List<Transaction> getTransactionsByCategory(Long categoryId) {
        return transactions.values().stream()
            .filter(t -> t.getCategory() != null && t.getCategory().getId().equals(categoryId))
            .collect(Collectors.toList());
    }
    
    public static List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end) {
        return transactions.values().stream()
            .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
            .collect(Collectors.toList());
    }
}