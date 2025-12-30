package com.banking.controller;

import com.banking.dto.transaction.BankerTransactionDTO;
import com.banking.dto.transaction.CustomerTransactionDTO;
import com.banking.dto.transaction.NewTransactionDTO;
import com.banking.exception.InsufficientFundsException;
import com.banking.model.Transaction;
import com.banking.repository.BankerRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import com.banking.security.BankingUserDetails;
import com.banking.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/transfer-{type}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String showTransferForm(Authentication authentication,
                                   Model model,
                                   @PathVariable String type) {
        // Validate type
        if (!type.equals("within") && !type.equals("external")) {
            return "redirect:/dashboard";
        }

        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();

        model.addAttribute("firstName", userDetails.getFirstName());

        model.addAttribute("formattedBalance",
                transactionService.getFormattedBalance(userDetails.getUserId()));

        if (type.equals("within")) {
            model.addAttribute("headerText", "Transfer to an Account Within Chicago Bank");
            model.addAttribute("transferType", "within");
        } else if (type.equals("external")) {
            model.addAttribute("headerText", "Transfer to an Account Outside Chicago Bank");
            model.addAttribute("transferType", "external");
        }

        return "transactions/transfer";
    }


    @PostMapping("/transfer-{type}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String processTransfer(Authentication authentication,
                                  Model model,
                                  @PathVariable String type,
                                  @Valid @ModelAttribute NewTransactionDTO transactionDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Collect error messages
            StringBuilder fieldErrorMessages = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                fieldErrorMessages.append(error.getDefaultMessage()).append(" ");
            }
            redirectAttributes.addFlashAttribute("fieldErrorMessages", fieldErrorMessages.toString());
            return "redirect:/transactions/transfer-" + type;
        }

        // Validate type
        if (!type.equals("within") && !type.equals("external")) {
            return "redirect:/dashboard";
        }

        try {
            Transaction transaction;
            if (type.equals("within")) {
                transaction = transactionService.transferWithin(transactionDTO, userDetails.getUserId());
                redirectAttributes.addFlashAttribute("successMessage",
                        "Transfer completed successfully!");
            } else {
                transaction = transactionService.transferExternal(transactionDTO, userDetails.getUserId());
                redirectAttributes.addFlashAttribute("successMessage",
                        "Transfer request submitted! Awaiting approval");
            }
            redirectAttributes.addFlashAttribute("transactionId", transaction.getTransactionId());
        } catch (InsufficientFundsException e){
            redirectAttributes.addFlashAttribute("errorMessageOverdraft", e.getMessage());
        }

        return "redirect:/transactions/transfer-" + type;
    }
}
