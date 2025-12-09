package com.banking.dto;

import com.banking.model.Transaction;

import java.util.List;

public class CustomerDashboardDTO {
    private String actno;
    private String firstName;
    private String formattedBalance;
    private List<Transaction> transactionsFromAccount;

    public CustomerDashboardDTO(String actno,
                                String firstName,
                                String formattedBalance,
                                List<Transaction> transactionsFromAccount)
    {
        this.actno = actno;
        this.firstName = firstName;
        this.formattedBalance = formattedBalance;
        this.transactionsFromAccount = transactionsFromAccount;
    }

    public String getActno() {
        return actno;
    }

    public void setActno(String actno) {
        this.actno = actno;
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
