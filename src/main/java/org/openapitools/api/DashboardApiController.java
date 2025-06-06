package org.openapitools.api;

import org.openapitools.model.DashboardSummary;
import org.openapitools.model.Transaction;
import org.openapitools.model.TransactionType;
import org.openapitools.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-22T17:38:09.228910+03:00[Europe/Moscow]", comments = "Generator version: 7.13.0")
@Controller
@RequestMapping("${openapi.budgetManagement.base-path:/api}")
public class DashboardApiController implements DashboardApi {

    private static final Logger log = LoggerFactory.getLogger(DashboardApiController.class);

    private final NativeWebRequest request;
    private final TransactionService transactionService;

    @Autowired
    public DashboardApiController(NativeWebRequest request, TransactionService transactionService) {
        this.request = request;
        this.transactionService = transactionService;
        log.info("DashboardApiController initialized with TransactionService");
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<DashboardSummary> getDashboardSummary() {
        log.debug("Getting dashboard summary");

        // Get totals from TransactionService
        Double totalIncome = transactionService.getTotalByType(TransactionType.INCOME);
        Double totalExpense = transactionService.getTotalByType(TransactionType.EXPENSE);
        Double balance = totalIncome - totalExpense;

        // Create dashboard summary
        DashboardSummary summary = new DashboardSummary(totalIncome, totalExpense, balance);

        // Add recent transactions (5 most recent)
        List<Transaction> recentTransactions = transactionService.getRecentTransactions(5);
        summary.setRecentTransactions(recentTransactions);

        log.info("Returning dashboard summary with balance: {}", balance);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }
}