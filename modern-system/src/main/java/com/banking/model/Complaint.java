package com.banking.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaintid", unique = true, nullable = false)
    private int complaintId;

    @Column(name = "complaint_no", unique = true, nullable = false)
    private int complaintNumber;

    @Column(name = "actno", nullable = false)
    private String accountNumber;

    @Column(name = "complaint_date")
    private LocalDate complaintDate;

    private String subject;

    private String description;

    private String status;

    private String closed;

    public Complaint(int complaintId, int complaintNumber, String accountNumber, LocalDate complaintDate, String subject, String description, String status, String closed) {
        this.complaintId = complaintId;
        this.complaintNumber = complaintNumber;
        this.accountNumber = accountNumber;
        this.complaintDate = complaintDate;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.closed = closed;
    }

    public Complaint() {
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public int getComplaintNumber() {
        return complaintNumber;
    }

    public void setComplaintNumber(int complaintNumber) {
        this.complaintNumber = complaintNumber;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }
}

