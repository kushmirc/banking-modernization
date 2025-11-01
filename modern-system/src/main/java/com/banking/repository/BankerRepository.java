package com.banking.repository;

import com.banking.model.Banker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankerRepository extends JpaRepository<Banker, String> {
    Optional<Banker> findByUserid(String userid);
    boolean existsByUserid(String userid);
}
