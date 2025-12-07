package com.banking.service;

import com.banking.dto.CustomerDashboardDTO;
import com.banking.model.Customer;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class DashboardService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDashboardDTO getCustomerDashboard(String username) {
        Customer customer = customerRepository.findByUserid(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));


        return new CustomerDashboardDTO(
                customer.getActno(),
                customer.getFname(),
                formatCurrency(customer.getBalance())
        );
    }

    private String formatCurrency(double balance) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(balance);
    }
}
