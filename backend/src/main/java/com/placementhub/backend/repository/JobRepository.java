package com.placementhub.backend.repository;

import com.placementhub.backend.entity.Company;
import com.placementhub.backend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface JobRepository extends JpaRepository<Job, Long> {


    List<Job> findByCompany(Company company);

    List<Job> findByCompanyId(Long companyId);

    Optional<Job> findByIdAndCompanyId(Long jobId, Long companyId);
}