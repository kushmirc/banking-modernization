package com.banking.controller;

import com.banking.model.Banker;
import com.banking.model.Complaint;
import com.banking.model.Customer;
import com.banking.repository.AdministratorRepository;
import com.banking.repository.BankerRepository;
import com.banking.repository.ComplaintRepository;
import com.banking.repository.CustomerRepository;
import com.banking.security.BankingUserDetails;
import com.banking.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    ComplaintService complaintService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BankerRepository bankerRepository;

    @Autowired
    AdministratorRepository administratorRepository;


    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String customerComplaints(Authentication authentication, Model model) {
        // Get the customer's first name
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());

        // Add list of complaints for the customer
        model.addAttribute("complaints", complaintService.getCustomerComplaints(userDetails.getUserId()));


        return "complaints/customer-complaints";
    }

    @GetMapping("/banker")
    @PreAuthorize("hasRole('BANKER')")
    public String bankerComplaints(Authentication authentication, Model model) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());
        List<Complaint> complaints = complaintRepository.findComplaintsByOrderByComplaintDateDesc();
        model.addAttribute("complaints", complaints);

        return "complaints/banker-complaints";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String adminComplaints(Authentication authentication, Model model){
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());

        List<Complaint> complaints = complaintRepository.findComplaintsByOrderByComplaintDateDesc();
        model.addAttribute("complaints", complaints);
        return "complaints/admin-complaints";

    }

    @GetMapping("/new")
    public String newComplaint(Model model) {
        return "complaints/new";
    }

}
