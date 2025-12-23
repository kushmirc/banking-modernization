package com.banking.service;

import com.banking.dto.transaction.CustomerTransactionDTO;
import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public List<CustomerTransactionDTO> getCustomerTransactions(String username){
        Customer customer = customerRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        List<Transaction> transactions = transactionRepository
                .findTransactionsByFromAccountNumberOrderByTransactionDateDesc(customer.getAccountNumber());

        List<CustomerTransactionDTO> customerTransactionDTOS = new ArrayList<>();

        for(Transaction transaction : transactions) {
            customerTransactionDTOS.add(new CustomerTransactionDTO(
                    transaction.getTransactionId(),
                    transaction.getToAccountNumber(),
                    transaction.getTransactionDate(),
                    transaction.getTransactionDescription(),
                    transaction.getTransactionStatus(),
                    formatCurrency(transaction.getAmount()),
                    transaction.getAmountAction()));
        }

        return customerTransactionDTOS;
    }

    private String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }
}
