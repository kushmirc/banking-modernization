package com.banking.dto.transaction;

public class NewTransactionDTO {

    private String toAccountNumber;

    private String formattedAmount;

    private String amountAction;

    public NewTransactionDTO(String toAccountNumber,
                             String formattedAmount,
                             String amountAction) {
        this.toAccountNumber = toAccountNumber;
        this.formattedAmount = formattedAmount;
        this.amountAction = amountAction;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public String getFormattedAmount() {
        return formattedAmount;
    }

    public void setFormattedAmount(String formattedAmount) {
        this.formattedAmount = formattedAmount;
    }

    public String getAmountAction() {
        return amountAction;
    }

    public void setAmountAction(String amountAction) {
        this.amountAction = amountAction;
    }
}
