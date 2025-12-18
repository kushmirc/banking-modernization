package com.banking.repository;

import com.banking.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    Optional<Complaint> findComplaintByComplaintId(int complaintId);

    List<Complaint> findTop5ByOrderByComplaintDateDesc();

    List<Complaint> findComplaintsByOrderByComplaintDateDesc();

    List<Complaint> findComplaintsByAccountNumberOrderByComplaintDateDesc(String accountNumber);

    @Query("SELECT MAX(c.complaintNumber) FROM Complaint c")
    Optional<Integer> findMaxComplaintNumber();
}
