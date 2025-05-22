package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.model.Transaction;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * DashboardSummary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-22T17:38:09.228910+03:00[Europe/Moscow]", comments = "Generator version: 7.13.0")
public class DashboardSummary {

  private Double totalIncome;

  private Double totalExpense;

  private Double balance;

  @Valid
  private List<@Valid Transaction> recentTransactions = new ArrayList<>();

  public DashboardSummary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DashboardSummary(Double totalIncome, Double totalExpense, Double balance) {
    this.totalIncome = totalIncome;
    this.totalExpense = totalExpense;
    this.balance = balance;
  }

  public DashboardSummary totalIncome(Double totalIncome) {
    this.totalIncome = totalIncome;
    return this;
  }

  /**
   * Get totalIncome
   * @return totalIncome
   */
  @NotNull 
  @Schema(name = "totalIncome", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalIncome")
  public Double getTotalIncome() {
    return totalIncome;
  }

  public void setTotalIncome(Double totalIncome) {
    this.totalIncome = totalIncome;
  }

  public DashboardSummary totalExpense(Double totalExpense) {
    this.totalExpense = totalExpense;
    return this;
  }

  /**
   * Get totalExpense
   * @return totalExpense
   */
  @NotNull 
  @Schema(name = "totalExpense", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalExpense")
  public Double getTotalExpense() {
    return totalExpense;
  }

  public void setTotalExpense(Double totalExpense) {
    this.totalExpense = totalExpense;
  }

  public DashboardSummary balance(Double balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
   */
  @NotNull 
  @Schema(name = "balance", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("balance")
  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public DashboardSummary recentTransactions(List<@Valid Transaction> recentTransactions) {
    this.recentTransactions = recentTransactions;
    return this;
  }

  public DashboardSummary addRecentTransactionsItem(Transaction recentTransactionsItem) {
    if (this.recentTransactions == null) {
      this.recentTransactions = new ArrayList<>();
    }
    this.recentTransactions.add(recentTransactionsItem);
    return this;
  }

  /**
   * Get recentTransactions
   * @return recentTransactions
   */
  @Valid 
  @Schema(name = "recentTransactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recentTransactions")
  public List<@Valid Transaction> getRecentTransactions() {
    return recentTransactions;
  }

  public void setRecentTransactions(List<@Valid Transaction> recentTransactions) {
    this.recentTransactions = recentTransactions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DashboardSummary dashboardSummary = (DashboardSummary) o;
    return Objects.equals(this.totalIncome, dashboardSummary.totalIncome) &&
        Objects.equals(this.totalExpense, dashboardSummary.totalExpense) &&
        Objects.equals(this.balance, dashboardSummary.balance) &&
        Objects.equals(this.recentTransactions, dashboardSummary.recentTransactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalIncome, totalExpense, balance, recentTransactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardSummary {\n");
    sb.append("    totalIncome: ").append(toIndentedString(totalIncome)).append("\n");
    sb.append("    totalExpense: ").append(toIndentedString(totalExpense)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    recentTransactions: ").append(toIndentedString(recentTransactions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

