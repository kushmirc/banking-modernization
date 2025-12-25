package com.banking.controller;

import com.banking.dto.transaction.BankerTransactionDTO;
import com.banking.dto.transaction.CustomerTransactionDTO;
import com.banking.dto.transaction.NewTransactionDTO;
import com.banking.repository.BankerRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import com.banking.security.BankingUserDetails;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BankerRepository bankerRepository;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String getCustomerTransactions(Authentication authentication, Model model) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());

        List<CustomerTransactionDTO> customerTransactionDTOS = transactionService
                .getCustomerTransactions(userDetails.getUserId());

        model.addAttribute("transactionsFromAccount", customerTransactionDTOS);

        return "transactions/customer-transactions";
    }

    @GetMapping("/banker")
    @PreAuthorize("hasRole('BANKER')")
    public String getBankerTransactions(Authentication authentication, Model model) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());

        List<BankerTransactionDTO> bankerTransactionDTOS = transactionService.getBankerTransactions();
        model.addAttribute("transactions", bankerTransactionDTOS);

        return"transactions/banker-transactions";
    }

    @GetMapping("/transfer-within")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String showTransferWithinForm(Authentication authentication, Model model) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();

        model.addAttribute("firstName", userDetails.getFirstName());

        model.addAttribute("formattedBalance",
                transactionService.getFormattedBalance(userDetails.getUserId()));

        return "transactions/transfer-within";
    }

    @PostMapping("/transfer-within")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String processTransferWithin(Authentication authentication,
                                        Model model,
                                        @ModelAttribute NewTransactionDTO transactionDTO) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();

        transactionService.transferWithin(transactionDTO, userDetails.getUserId());

        return "transactions/transfer-within";
    }

}
