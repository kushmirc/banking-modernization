package com.banking.controller;

import com.banking.dto.transaction.BankerTransactionDTO;
import com.banking.dto.transaction.CustomerTransactionDTO;
import com.banking.dto.transaction.NewTransactionDTO;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.InsufficientFundsException;
import com.banking.exception.ResourceNotFoundException;
import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.repository.BankerRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import com.banking.security.BankingUserDetails;
import com.banking.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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

    private String collectFieldErrorMessages(BindingResult bindingResult) {
        StringBuilder messages = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            messages.append(error.getDefaultMessage()).append(" ");
        }
        return messages.toString().trim();
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
                                  @PathVariable String type,
                                  @Valid @ModelAttribute NewTransactionDTO transactionDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrorMessages",
                    collectFieldErrorMessages(bindingResult));
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
        } catch (AccountNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessageUser", e.getMessage());
            redirectAttributes.addFlashAttribute("accountNumber", transactionDTO.getToAccountNumber());
        }

        return "redirect:/transactions/transfer-" + type;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('BANKER')")
    public String addCustomerTransaction(Authentication authentication,
                                         Model model) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());

        return "transactions/add-transaction";
    }

    // Display the customer's balance on Check Balance button click
    @GetMapping("/add/{accountNumber}")
    @PreAuthorize("hasRole('BANKER')")
    public String addCustomerTransaction(Authentication authentication,
                                         Model model,
                                         @PathVariable String accountNumber,
                                         RedirectAttributes redirectAttributes) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());

        Optional<Customer> customerOpt = customerRepository.findByAccountNumber(accountNumber);
        if (customerOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("fieldErrorMessages",
                    "Account number not found: " + accountNumber);
            return "redirect:/transactions/add";
        }

        Customer customer = customerOpt.get();
        model.addAttribute("formattedBalance",
                transactionService.getFormattedBalance(customer.getUserId()));
        model.addAttribute("accountNumber", accountNumber);

        return "transactions/add-transaction";
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('BANKER')")
    public String processAddTransaction(@Valid @ModelAttribute NewTransactionDTO transactionDTO,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrorMessages",
                    collectFieldErrorMessages(bindingResult));
            redirectAttributes.addFlashAttribute("accountNumber",
                    transactionDTO.getToAccountNumber());
            return "redirect:/transactions/add";
        }

        // Check for Transaction Type error
        if (!transactionDTO.getAmountAction().equals("Debit") &&
                !transactionDTO.getAmountAction().equals("Credit")) {
            redirectAttributes.addFlashAttribute("errorMessageType",
                    "Please select 'Debit' or 'Credit'");
            return "redirect:/transactions/add";
        }

        // Process the transaction
        try {
            Transaction transaction;
            transaction = transactionService.addCustomerTransaction(transactionDTO);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Transaction completed successfully!");
            redirectAttributes.addFlashAttribute("transactionId",
                    transaction.getTransactionId());
        } catch (InsufficientFundsException e) {
            redirectAttributes.addFlashAttribute("errorMessageOverdraft", e.getMessage());
        } catch (AccountNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessageUser", e.getMessage());
        }

        return "redirect:/transactions/add";
    }


    @GetMapping("/review")
    @PreAuthorize("hasRole('BANKER')")
    public String reviewExternalTransfers(Authentication authentication, Model model) {
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        model.addAttribute("firstName", userDetails.getFirstName());

        List<BankerTransactionDTO> transactionDTOS = transactionService
                .getExternalTransfersForReview();

        model.addAttribute("transactions", transactionDTOS);
        return "transactions/review-external-transfers";
    }

    @PutMapping("{transactionId}/status")
    @PreAuthorize("hasRole('BANKER')")
    @ResponseBody
    public ResponseEntity<?> updateExternalTransferStatus(
            @PathVariable Integer transactionId,
            @RequestParam String status) {

        try{
            Transaction updateTransaction = transactionService.updateTransactionStatus(transactionId, status);
            return ResponseEntity.ok(updateTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}


