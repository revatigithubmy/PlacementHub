package com.placementhub.backend.controller;

import com.placementhub.backend.dto.JobRequest;
import com.placementhub.backend.dto.JobResponse;
import com.placementhub.backend.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            Authentication authentication,
            @Valid @RequestBody JobRequest request
    ) {

        String email = authentication.getName();

        JobResponse response =
                jobService.createJob(email, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getMyJobs(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                jobService.getMyJobs(email)
        );
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJob(
            Authentication authentication,
            @PathVariable Long jobId
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                jobService.getJob(email, jobId)
        );
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> updateJob(
            Authentication authentication,
            @PathVariable Long jobId,
            @Valid @RequestBody JobRequest request
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                jobService.updateJob(
                        email,
                        jobId,
                        request
                )
        );
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(
            Authentication authentication,
            @PathVariable Long jobId
    ) {

        String email = authentication.getName();

        jobService.deleteJob(email, jobId);

        return ResponseEntity.noContent().build();
    }
}