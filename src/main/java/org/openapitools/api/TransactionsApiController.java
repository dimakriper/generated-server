package org.openapitools.api;

import org.openapitools.dao.CategoryDAO;
import org.openapitools.dao.TransactionDAO;
import org.openapitools.model.Category;
import org.openapitools.model.Error;
import org.openapitools.model.Transaction;
import org.openapitools.model.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-22T17:38:09.228910+03:00[Europe/Moscow]", comments = "Generator version: 7.13.0")
@Controller
@RequestMapping("${openapi.budgetManagement.base-path:/api}")
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);
    
    private final NativeWebRequest request;

    @Autowired
    public TransactionsApiController(NativeWebRequest request) {
        this.request = request;
        log.info("TransactionsApiController initialized with TransactionDAO");
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }
    
    @Override
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        log.debug("Creating new transaction: {}", transactionRequest);
        
        // Create a new transaction from the request
        Transaction newTransaction = new Transaction();
        newTransaction.setDescription(transactionRequest.getDescription());
        newTransaction.setAmount(transactionRequest.getAmount());
        newTransaction.setDate(transactionRequest.getDate());
        newTransaction.setType(transactionRequest.getType());
        
        // Set category if categoryId is provided
        if (transactionRequest.getCategoryId() != null) {
            Category category = CategoryDAO.findById(transactionRequest.getCategoryId());
            newTransaction.setCategory(category);
        }
        
        // Add transaction to DAO
        Transaction created = TransactionDAO.addTransaction(newTransaction);
        
        log.info("Created transaction with ID: {}", created.getId());
        
        // Return with 201 Created status
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @Override
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id) {
        log.debug("Deleting transaction with ID: {}", id);
        
        if (TransactionDAO.findById(id) == null) {
            log.warn("Transaction not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        boolean deleted = TransactionDAO.deleteTransaction(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        log.info("Deleted transaction with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Override
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        log.debug("Getting all transactions");
        
        List<Transaction> transactions = TransactionDAO.getAllTransactions();
        log.info("Returning {} transactions", transactions.size());
        
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Long id) {
        log.debug("Getting transaction with ID: {}", id);
        
        Transaction transaction = TransactionDAO.findById(id);
        
        if (transaction == null) {
            log.warn("Transaction not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        log.info("Found transaction with ID: {}", id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable("id") Long id,
            @Valid @RequestBody TransactionRequest transactionRequest) {
        log.debug("Updating transaction with ID: {}", id);
        
        Transaction existingTransaction = TransactionDAO.findById(id);
        
        if (existingTransaction == null) {
            log.warn("Transaction not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Update the transaction properties
        existingTransaction.setDescription(transactionRequest.getDescription());
        existingTransaction.setAmount(transactionRequest.getAmount());
        existingTransaction.setDate(transactionRequest.getDate());
        existingTransaction.setType(transactionRequest.getType());
        
        // Update category if categoryId is provided
        if (transactionRequest.getCategoryId() != null) {
            Category category = CategoryDAO.findById(transactionRequest.getCategoryId());
            existingTransaction.setCategory(category);
        } else {
            existingTransaction.setCategory(null);
        }
        
        // Update the transaction in the DAO
        boolean updated = TransactionDAO.updateTransaction(existingTransaction);
        
        if (!updated) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        log.info("Updated transaction with ID: {}", id);
        return new ResponseEntity<>(existingTransaction, HttpStatus.OK);
    }
}