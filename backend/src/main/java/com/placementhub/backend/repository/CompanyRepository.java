package com.placementhub.backend.repository;

import com.placementhub.backend.entity.Company;
import com.placementhub.backend.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByRecruiter(Recruiter recruiter);

    Optional<Company> findByRecruiterId(Long recruiterId);

    Optional<Company> findByRecruiterUserId(Long userId);

    boolean existsByRecruiterId(Long recruiterId);
}