package com.banking.dto.complaint;

import java.time.LocalDate;

public class NewComplaintDTO {
    private String accountNumber;
    private LocalDate complaintDate;
    private String subject;
    private String description;

    public NewComplaintDTO(String accountNumber, LocalDate complaintDate, String subject, String description) {
        this.accountNumber = accountNumber;
        this.complaintDate = complaintDate;
        this.subject = subject;
        this.description = description;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
