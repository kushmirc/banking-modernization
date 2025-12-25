package com.banking.repository;

import com.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findTransactionByTransactionId(int transactionId);

    List<Transaction> findTop5ByFromAccountNumberOrderByTransactionDateDesc(String fromAccountNumber);

    List<Transaction> findTransactionsByFromAccountNumberOrderByTransactionDateDesc(String fromAccountNumber);

    List<Transaction> findAllByOrderByTransactionDateDesc();

    @Query("SELECT MAX(t.transactionId) FROM Transaction t")
    Optional<Integer> findMaxTransactionId();
}

