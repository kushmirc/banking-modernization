package com.banking.service;

import com.banking.model.Complaint;
import com.banking.model.Customer;
import com.banking.repository.ComplaintRepository;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ComplaintRepository complaintRepository;

    Authentication authentication;

    public List<Complaint> getCustomerComplaints(String userId) {
        // Get the customer with the userId of the user
        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        // Get the customer's account number
        String  accountNumber = customer.getAccountNumber();

        return complaintRepository.findComplaintsByAccountNumberOrderByComplaintDateDesc(accountNumber);
    }
}
