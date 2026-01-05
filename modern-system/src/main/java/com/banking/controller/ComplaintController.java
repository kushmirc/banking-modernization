package com.banking.controller;

import com.banking.dto.complaint.ComplaintStatusUpdateDTO;
import com.banking.dto.complaint.NewComplaintDTO;
import com.banking.model.Complaint;
import com.banking.model.Customer;
import com.banking.repository.AdministratorRepository;
import com.banking.repository.BankerRepository;
import com.banking.repository.ComplaintRepository;
import com.banking.repository.CustomerRepository;
import com.banking.security.BankingUserDetails;
import com.banking.service.ComplaintService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // Modify a complaint (Administrator)
    @PutMapping("{complaintId}/status")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseBody
    public ResponseEntity<?> updateComplaintStatus(
            @PathVariable Integer complaintId,
            @RequestBody ComplaintStatusUpdateDTO request) {

        try {
            Complaint updatedComplaint = complaintService.updateComplaintStatus(
                    complaintId,
                    request.getStatus(),
                    request.getClosed());
            return ResponseEntity.ok(updatedComplaint);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Register a new complaint
    @GetMapping("/new")
    public String newComplaint(Authentication authentication, Model model) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());
        String accountNumber = "";
        boolean isCustomer = false;

        if(userDetails.getRole().equals("CUSTOMER")) {
            Customer customer = customerRepository.findByUserId(userDetails.getUserId())
                    .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
            accountNumber = customer.getAccountNumber();
            isCustomer = true;
        }

        model.addAttribute("accountNumber", accountNumber);
        model.addAttribute("isCustomer", isCustomer);
        return "complaints/new-complaint";
    }

    // Register a new complaint
    @PostMapping("/new")
    public String createComplaint(@ModelAttribute NewComplaintDTO newComplaintDTO,
                                  RedirectAttributes redirectAttributes) {

        Complaint complaint = complaintService.createComplaint(newComplaintDTO);

        redirectAttributes.addFlashAttribute("successMessage",
                                             "Complaint #"
                                                     + complaint.getComplaintNumber()
                                                     + " registered successfully!");
        redirectAttributes.addFlashAttribute("complaintNumber", complaint.getComplaintNumber());

        return "redirect:/complaints/new";
    }

}
