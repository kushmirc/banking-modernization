package com.banking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "tranid", unique = true, nullable = false)
    private int transactionId;

    @Column(name = "fromactno", unique = false, nullable = false)
    private String fromAccountNumber;

    @Column(name = "toactno", unique = false, nullable = false)
    private String toAccountNumber;

    @Column(name = "trandate", unique = false, nullable = false)
    private LocalDate transactionDate;

    @Column(name = "trandesc", unique = false, nullable = true)
    private String transactionDescription;

    @Column(name = "transtatus", unique = false, nullable = false)
    private String transactionStatus;

    @Column(unique = false, nullable = true)
    private String remark;

    @Column(unique = false, nullable = false)
    private double amount;

    @Column(name = "amountaction", unique = false, nullable = false)
    private String amountAction;

    public Transaction(int transactionId, String fromAccountNumber, String toAccountNumber, LocalDate transactionDate, String transactionDescription, String transactionStatus, String remark, double amount, String amountAction) {
        this.transactionId = transactionId;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.transactionDate = transactionDate;
        this.transactionDescription = transactionDescription;
        this.transactionStatus = transactionStatus;
        this.remark = remark;
        this.amount = amount;
        this.amountAction = amountAction;
    }

    public Transaction() {

    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
