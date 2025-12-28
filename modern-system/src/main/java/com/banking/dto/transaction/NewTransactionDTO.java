package com.banking.dto.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class NewTransactionDTO {

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Account number must contain only digits.")
    private String toAccountNumber;

    @NotBlank(message = "Amount is required")
    @Pattern(regexp = "^[0-9]+$", message = "Amount must be a whole number (no decimals).")
    private String formattedAmount;

    private String amountAction;

    // Default constructor needed for Spring form binding
    public NewTransactionDTO() {}

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
