package com.banking.dto;

import com.banking.model.Transaction;

import java.util.List;

public class CustomerDashboardDTO {
    private String accountNumber;
    private String firstName;
    private String formattedBalance;
    private List<Transaction> transactionsFromAccount;

    public CustomerDashboardDTO(String accountNumber,
                                String firstName,
                                String formattedBalance,
                                List<Transaction> transactionsFromAccount)
    {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.formattedBalance = formattedBalance;
        this.transactionsFromAccount = transactionsFromAccount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFormattedBalance() {
        return formattedBalance;
    }

    public void setFormattedBalance(String balance) {
        this.formattedBalance = balance;
    }

    public List<Transaction> getTransactionsFromAccount() {
        return transactionsFromAccount;
    }

    public void setTransactionsFromAccount(List<Transaction> transactionsFromAccount) {
        this.transactionsFromAccount = transactionsFromAccount;
    }
}
