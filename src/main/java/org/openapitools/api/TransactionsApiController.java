package org.openapitools.api;

import org.openapitools.model.Transaction;
import org.openapitools.model.TransactionRequest;
import org.openapitools.service.TransactionService;
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
    private final TransactionService transactionService;

    @Autowired
    public TransactionsApiController(NativeWebRequest request, TransactionService transactionService) {
        this.request = request;
        this.transactionService = transactionService;
        log.info("TransactionsApiController initialized with TransactionService");
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        log.debug("Creating new transaction: {}", transactionRequest);

        try {
            Transaction created = transactionService.createTransaction(transactionRequest);
            log.info("Created transaction with ID: {}", created.getId());
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid transaction request: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error creating transaction: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id) {
        log.debug("Deleting transaction with ID: {}", id);

        Optional<Transaction> transactionOpt = transactionService.findById(id);
        if (!transactionOpt.isPresent()) {
            log.warn("Transaction not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean deleted = transactionService.deleteTransaction(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Deleted transaction with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        log.debug("Getting all transactions");

        List<Transaction> transactions = transactionService.getAllTransactions();
        log.info("Returning {} transactions", transactions.size());

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Long id) {
        log.debug("Getting transaction with ID: {}", id);

        Optional<Transaction> transactionOpt = transactionService.findById(id);

        if (!transactionOpt.isPresent()) {
            log.warn("Transaction not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Found transaction with ID: {}", id);
        return new ResponseEntity<>(transactionOpt.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable("id") Long id,
            @Valid @RequestBody TransactionRequest transactionRequest) {
        log.debug("Updating transaction with ID: {}", id);

        try {
            Optional<Transaction> updatedOpt = transactionService.updateTransaction(id, transactionRequest);
            
            if (!updatedOpt.isPresent()) {
                log.warn("Transaction not found with ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            log.info("Updated transaction with ID: {}", id);
            return new ResponseEntity<>(updatedOpt.get(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid transaction request for ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error updating transaction with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}