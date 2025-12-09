package com.banking.repository;

import com.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findTransactionByTransactionId(int transactionId);

    List<Transaction> findTop5ByFromAccountNumberOrderByTransactionDateDesc(String fromAccountNumber);

}
