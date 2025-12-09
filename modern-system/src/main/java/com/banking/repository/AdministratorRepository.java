package com.banking.repository;

import com.banking.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String>{
    Optional<Administrator> findByUserId(String userid);
    boolean existsByUserId(String userid);
}
