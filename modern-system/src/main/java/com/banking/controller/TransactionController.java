package com.banking.controller;

import com.banking.repository.BankerRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import com.banking.security.BankingUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BankerRepository bankerRepository;

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String getCustomerTransactions(Authentication authentication, Model model) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());

        return "transactions/customer-transactions";
    }
}
