package com.banking.service;

import com.banking.dto.AdministratorDashboardDTO;
import com.banking.dto.CustomerDashboardDTO;
import com.banking.model.Administrator;
import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.repository.AdministratorRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public CustomerDashboardDTO getCustomerDashboard(String username) {
        Customer customer = customerRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        List<Transaction> transactionsFromCustomer = transactionRepository
                .findTop5ByFromAccountNumberOrderByTransactionDateDesc(customer.getAccountNumber());


        return new CustomerDashboardDTO(
                customer.getAccountNumber(),
                customer.getFirstName(),
                formatCurrency(customer.getBalance()),
                transactionsFromCustomer
        );
    }

    public AdministratorDashboardDTO getAdministratorDashboard(String username) {
        Administrator administrator = administratorRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Administrator not found"));

        return new AdministratorDashboardDTO(
                administrator.getFirstName()
        );
    }

    private String formatCurrency(double balance) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(balance);
    }
}
