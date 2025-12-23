package com.banking.dto.transaction;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class CustomerTransactionDTO {
    private int transactionId;

    private String toAccountNumber;

    private LocalDate transactionDate;

    private String transactionDescription;

    private String transactionStatus;

    private double amount;

    private String amountAction;

    public CustomerTransactionDTO(int transactionId,
                                  String toAccountNumber,
                                  LocalDate transactionDate,
                                  String transactionDescription,
                                  String transactionStatus,
                                  double amount,
                                  String amountAction) {
        this.transactionId = transactionId;
        this.toAccountNumber = toAccountNumber;
        this.transactionDate = transactionDate;
        this.transactionDescription = transactionDescription;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.amountAction = amountAction;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountAction() {
        return amountAction;
    }

    public void setAmountAction(String amountAction) {
        this.amountAction = amountAction;
    }
}
