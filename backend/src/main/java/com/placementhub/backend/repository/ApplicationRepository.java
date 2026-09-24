package com.placementhub.backend.repository;

import com.placementhub.backend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByStudentIdAndJobId(Long studentId, Long jobId);

    List<Application> findByStudentId(Long studentId);

    List<Application> findByJobId(Long jobId);

    Optional<Application> findByIdAndStudentId(Long applicationId, Long studentId);

    List<Application> findByJobIdAndStatus(
            Long jobId,
            Application.ApplicationStatus status
    );

    long countByStatus(Application.ApplicationStatus status);
}