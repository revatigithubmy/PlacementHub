package com.placementhub.backend.repository;

import com.placementhub.backend.entity.Recruiter;
import com.placementhub.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
    Optional<Recruiter> findByUser(User user);

    Optional<Recruiter> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}