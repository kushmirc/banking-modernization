package com.banking.service;

import com.banking.dto.complaint.NewComplaintDTO;
import com.banking.model.Complaint;
import com.banking.model.Customer;
import com.banking.repository.ComplaintRepository;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ComplaintRepository complaintRepository;


    public List<Complaint> getCustomerComplaints(String userId) {
        // Get the customer with the userId of the user
        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        // Get the customer's account number
        String  accountNumber = customer.getAccountNumber();

        return complaintRepository.findComplaintsByAccountNumberOrderByComplaintDateDesc(accountNumber);
    }

    public Complaint updateComplaintStatus(Integer complaintId, String status, String closed){
        // Find the complaint
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new UsernameNotFoundException("Complaint not found"));

        // Validate status/closed logical rule
        validateStatusClosedCombination(status, closed);

        // Update and save
        complaint.setStatus(status);
        complaint.setClosed(closed);
        return complaintRepository.save(complaint);

    }

    private void validateStatusClosedCombination(String status, String closed) {
        if(status.equals("Closed") && closed.equals("False"))
            throw new IllegalArgumentException("Closed must be 'True' when Status is 'Closed'");
        if(status.equals("Open") && closed.equals("True"))
            throw new IllegalArgumentException("Closed must be 'False' when Status is 'Open'");
    }


    public Complaint createComplaint(NewComplaintDTO newComplaintDTO) {
        // Generate next complaint number
        int nextComplaintNumber = complaintRepository.findMaxComplaintNumber()
                .orElse(1000) +1;

        Complaint complaint = new Complaint();
        complaint.setComplaintNumber(nextComplaintNumber);
        complaint.setAccountNumber(newComplaintDTO.getAccountNumber());
        complaint.setComplaintDate(newComplaintDTO.getComplaintDate());
        complaint.setSubject(newComplaintDTO.getSubject());
        complaint.setDescription(newComplaintDTO.getDescription());
        complaint.setStatus("Open");
        complaint.setClosed("False");

        return complaintRepository.save(complaint);
    }
}
