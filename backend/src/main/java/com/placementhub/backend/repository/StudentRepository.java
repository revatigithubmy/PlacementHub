package com.placementhub.backend.repository;

import com.placementhub.backend.entity.Student;
import com.placementhub.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);

    Optional<Student> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}