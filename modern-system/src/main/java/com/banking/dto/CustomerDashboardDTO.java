package com.banking.dto;

public class CustomerDashboardDTO {
    private String actno;
    private String firstName;
    private String formattedBalance;

    public CustomerDashboardDTO(String actno,
                                String firstName,
                                String formattedBalance)
    {
        this.actno = actno;
        this.firstName = firstName;
        this.formattedBalance = formattedBalance;
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
}
