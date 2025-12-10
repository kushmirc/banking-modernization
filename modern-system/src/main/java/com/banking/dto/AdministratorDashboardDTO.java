package com.banking.dto;

import com.banking.model.Complaint;

import java.util.List;

public class AdministratorDashboardDTO {
    private String firstName;
    private List<Complaint> recentComplaints;
    private List<Complaint> allComplaints;

    public AdministratorDashboardDTO(String firstName,
                                     List<Complaint> recentComplaints,
                                     List<Complaint> allComplaints) {
        this.firstName = firstName;
        this.recentComplaints = recentComplaints;
        this.allComplaints = allComplaints;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Complaint> getRecentComplaints() {
        return recentComplaints;
    }

    public void setRecentComplaints(List<Complaint> recentComplaints) {
        this.recentComplaints = recentComplaints;
    }

    public List<Complaint> getAllComplaints() {
        return allComplaints;
    }

    public void setAllComplaints(List<Complaint> allComplaints) {
        this.allComplaints = allComplaints;
    }
}
