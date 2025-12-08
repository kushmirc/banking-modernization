package com.banking.dto;

public class AdministratorDashboardDTO {
    private String firstName;

    public AdministratorDashboardDTO(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
