package com.placementhub.backend.controller;

import com.placementhub.backend.dto.ApplicationResponse;
import com.placementhub.backend.dto.ApplicationStatusRequest;
import com.placementhub.backend.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterApplicationController {

    private final ApplicationService applicationService;

    public RecruiterApplicationController(
            ApplicationService applicationService
    ) {
        this.applicationService = applicationService;
    }

    // View applications for a job
    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<List<ApplicationResponse>>
    getApplicationsForJob(
            @PathVariable Long jobId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                applicationService.getApplicationsForJob(
                        jobId,
                        authentication
                )
        );
    }

    // View one application/applicant details
    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<ApplicationResponse>
    getRecruiterApplication(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                applicationService.getRecruiterApplication(
                        applicationId,
                        authentication
                )
        );
    }
    @PutMapping("/applications/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody ApplicationStatusRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                applicationService.updateApplicationStatus(
                        applicationId,
                        request,
                        authentication
                )
        );
    }
}