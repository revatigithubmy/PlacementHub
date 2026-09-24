package com.placementhub.backend.controller;

import com.placementhub.backend.dto.AdminCompanyResponse;
import com.placementhub.backend.dto.AdminJobResponse;
import com.placementhub.backend.dto.AdminRecruiterResponse;
import com.placementhub.backend.dto.AdminStudentResponse;
import com.placementhub.backend.dto.AdminUserResponse;
import com.placementhub.backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<AdminUserResponse> getUserById(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                adminService.getUserById(userId)
        );
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long userId
    ) {

        adminService.deleteUser(userId);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }

    @GetMapping("/students")
    public ResponseEntity<List<AdminStudentResponse>> getAllStudents() {

        return ResponseEntity.ok(
                adminService.getAllStudents()
        );
    }

    @GetMapping("/recruiters")
    public ResponseEntity<List<AdminRecruiterResponse>> getAllRecruiters() {

        return ResponseEntity.ok(
                adminService.getAllRecruiters()
        );
    }

    @GetMapping("/companies")
    public ResponseEntity<List<AdminCompanyResponse>> getAllCompanies() {

        return ResponseEntity.ok(
                adminService.getAllCompanies()
        );
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<AdminJobResponse>> getAllJobs() {

        return ResponseEntity.ok(
                adminService.getAllJobs()
        );
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long jobId
    ) {

        adminService.deleteJob(jobId);

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }
}