package com.banking.dto;

public class ComplaintStatusUpdateDTO {
    private String status;
    private String closed;

    public ComplaintStatusUpdateDTO(String status, String closed) {
        this.status = status;
        this.closed = closed;
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
