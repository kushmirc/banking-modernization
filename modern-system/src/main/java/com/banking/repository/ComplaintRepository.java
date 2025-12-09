package com.banking.repository;

import com.banking.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    Optional<Complaint> findComplaintByComplaintId(int complaintId);

    List<Complaint> findTop5OrderByComplaintDateDesc();

}
