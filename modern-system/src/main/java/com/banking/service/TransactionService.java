package com.banking.service;

import com.banking.dto.transaction.BankerTransactionDTO;
import com.banking.dto.transaction.CustomerTransactionDTO;
import com.banking.dto.transaction.NewTransactionDTO;
import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TransactionService {

    private final CustomerRepository customerRepository;

    private final TransactionRepository transactionRepository;

    // I'm using constructor dependency injection here, even though @Autowired is used elsewhere
    public TransactionService(CustomerRepository customerRepository,
                              TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

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

    public List<BankerTransactionDTO> getBankerTransactions() {
        List<Transaction> transactions = transactionRepository
                .findAllByOrderByTransactionDateDesc();

        List<BankerTransactionDTO> bankerTransactionDTOS = new ArrayList<>();

        for(Transaction transaction : transactions) {
            bankerTransactionDTOS.add(new BankerTransactionDTO(
                    transaction.getTransactionId(),
                    transaction.getFromAccountNumber(),
                    transaction.getToAccountNumber(),
                    transaction.getTransactionDate(),
                    transaction.getTransactionDescription(),
                    transaction.getTransactionStatus(),
                    transaction.getRemark(),
                    formatCurrency(transaction.getAmount()),
                    transaction.getAmountAction()
            ));
        }
        return bankerTransactionDTOS;
    }

    private String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }


    public String getFormattedBalance(String username) {
        Customer customer = customerRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
        return formatCurrency(customer.getBalance());
    }

    public boolean isOverdraft(NewTransactionDTO transactionDTO, String userId) {
        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        double transferAmount = Double.parseDouble(transactionDTO.getFormattedAmount());

        return transferAmount > customer.getBalance();
    }

    public Transaction transferWithin(NewTransactionDTO transactionDTO, String userId) {
        // Get the initiating customer object
        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        // Instantiate first Transaction object, build field values, and save
        Transaction transaction1 = new Transaction();
        transaction1.setFromAccountNumber(customer.getAccountNumber());
        transaction1.setToAccountNumber(transactionDTO.getToAccountNumber());
        transaction1.setTransactionDate(LocalDateTime.now());
        transaction1.setTransactionDescription("Funds Transfer Within the Bank");
        transaction1.setTransactionStatus("pass");
        transaction1.setRemark("Funds transferred successfully");
        transaction1.setAmount(Double.parseDouble(transactionDTO.getFormattedAmount()));
        transaction1.setAmountAction("debit");

        transactionRepository.save(transaction1);

        // Create second Transaction for the transfer (receiver's transaction)
        Transaction transaction2 = new Transaction();
        transaction2.setFromAccountNumber(transactionDTO.getToAccountNumber());
        transaction2.setToAccountNumber(customer.getAccountNumber());
        transaction2.setTransactionDate(LocalDateTime.now());
        transaction2.setTransactionDescription("Funds Transfer Within the Bank");
        transaction2.setTransactionStatus("pass");
        transaction2.setRemark("Funds transferred successfully");
        transaction2.setAmount(Double.parseDouble(transactionDTO.getFormattedAmount()));
        transaction2.setAmountAction("credit");

        transactionRepository.save(transaction2);

        return transaction1;
    }

}
