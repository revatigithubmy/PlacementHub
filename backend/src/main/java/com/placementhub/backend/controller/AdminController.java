package com.placementhub.backend.controller;

import com.placementhub.backend.dto.AdminApplicationResponse;
import com.placementhub.backend.dto.AdminCompanyResponse;
import com.placementhub.backend.dto.AdminDashboardResponse;
import com.placementhub.backend.dto.AdminJobResponse;
import com.placementhub.backend.dto.AdminRecruiterResponse;
import com.placementhub.backend.dto.AdminStudentResponse;
import com.placementhub.backend.dto.AdminUserResponse;
import com.placementhub.backend.entity.Application.ApplicationStatus;
import com.placementhub.backend.entity.User.Role;
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


    // API 1 - Get all users
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }


    // API 2 - Get user by ID
    @GetMapping("/users/{userId}")
    public ResponseEntity<AdminUserResponse> getUserById(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                adminService.getUserById(userId)
        );
    }


    // API 3 - Delete user
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long userId
    ) {

        adminService.deleteUser(userId);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }


    // API 4 - Get all students
    @GetMapping("/students")
    public ResponseEntity<List<AdminStudentResponse>> getAllStudents() {

        return ResponseEntity.ok(
                adminService.getAllStudents()
        );
    }


    // API 5 - Get all recruiters
    @GetMapping("/recruiters")
    public ResponseEntity<List<AdminRecruiterResponse>> getAllRecruiters() {

        return ResponseEntity.ok(
                adminService.getAllRecruiters()
        );
    }


    // API 6 - Get all companies
    @GetMapping("/companies")
    public ResponseEntity<List<AdminCompanyResponse>> getAllCompanies() {

        return ResponseEntity.ok(
                adminService.getAllCompanies()
        );
    }


    // API 7 - Get all jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<AdminJobResponse>> getAllJobs() {

        return ResponseEntity.ok(
                adminService.getAllJobs()
        );
    }


    // API 8 - Delete job
    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long jobId
    ) {

        adminService.deleteJob(jobId);

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }


    // API 11.1 - Dashboard statistics
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboardStats() {

        return ResponseEntity.ok(
                adminService.getDashboardStats()
        );
    }


    // API 11.2 - Get all applications
    @GetMapping("/applications")
    public ResponseEntity<List<AdminApplicationResponse>>
    getAllApplications() {

        return ResponseEntity.ok(
                adminService.getAllApplications()
        );
    }


    // API 11.3 - Get application by ID
    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<AdminApplicationResponse>
    getApplicationById(
            @PathVariable Long applicationId
    ) {

        return ResponseEntity.ok(
                adminService.getApplicationById(applicationId)
        );
    }


    // API 11.4 - Filter applications by status
    @GetMapping("/applications/status/{status}")
    public ResponseEntity<List<AdminApplicationResponse>>
    getApplicationsByStatus(
            @PathVariable ApplicationStatus status
    ) {

        return ResponseEntity.ok(
                adminService.getApplicationsByStatus(status)
        );
    }


    // API 11.5 - Applications for specific job
    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<List<AdminApplicationResponse>>
    getApplicationsForJob(
            @PathVariable Long jobId
    ) {

        return ResponseEntity.ok(
                adminService.getApplicationsForJob(jobId)
        );
    }


    // API 11.6 - Jobs for specific company
    @GetMapping("/companies/{companyId}/jobs")
    public ResponseEntity<List<AdminJobResponse>>
    getJobsForCompany(
            @PathVariable Long companyId
    ) {

        return ResponseEntity.ok(
                adminService.getJobsForCompany(companyId)
        );
    }


    // API 11.7 - Search users by email
    @GetMapping("/users/search")
    public ResponseEntity<List<AdminUserResponse>>
    searchUsersByEmail(
            @RequestParam String email
    ) {

        return ResponseEntity.ok(
                adminService.searchUsersByEmail(email)
        );
    }


    // API 11.8 - Filter users by role
    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<AdminUserResponse>>
    getUsersByRole(
            @PathVariable Role role
    ) {

        return ResponseEntity.ok(
                adminService.getUsersByRole(role)
        );
    }
}