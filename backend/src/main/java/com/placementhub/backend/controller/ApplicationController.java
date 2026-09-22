package com.placementhub.backend.controller;

import com.placementhub.backend.dto.ApplicationResponse;
import com.placementhub.backend.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService
    ) {
        this.applicationService = applicationService;
    }

    // Apply for a job
    @PostMapping("/{jobId}")
    public ResponseEntity<ApplicationResponse> applyForJob(
            @PathVariable Long jobId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                applicationService.applyForJob(
                        jobId,
                        authentication
                )
        );
    }

    // View all my applications
    @GetMapping
    public ResponseEntity<List<ApplicationResponse>>
    getMyApplications(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                applicationService.getMyApplications(
                        authentication
                )
        );
    }

    // View one application
    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponse>
    getMyApplication(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                applicationService.getMyApplication(
                        applicationId,
                        authentication
                )
        );
    }

    // Withdraw application
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<String> withdrawApplication(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {

        applicationService.withdrawApplication(
                applicationId,
                authentication
        );

        return ResponseEntity.ok(
                "Application withdrawn successfully"
        );
    }
}