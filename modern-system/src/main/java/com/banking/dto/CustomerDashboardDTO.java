package com.banking.dto;

public class CustomerDashboardDTO {
    private String actno;
    private String firstName;
    private double balance;

    public CustomerDashboardDTO(String actno,
                                String firstName,
                                double balance)
    {
        this.actno = actno;
        this.firstName = firstName;
        this.balance = balance;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
