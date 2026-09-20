package com.placementhub.backend.controller;

import com.placementhub.backend.dto.JobSearchResponse;
import com.placementhub.backend.service.JobSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobSearchController {

    private final JobSearchService jobSearchService;

    public JobSearchController(
            JobSearchService jobSearchService
    ) {
        this.jobSearchService = jobSearchService;
    }

    @GetMapping
    public ResponseEntity<List<JobSearchResponse>> getAllJobs() {

        return ResponseEntity.ok(
                jobSearchService.getAllJobs()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobSearchResponse>> searchJobs(
            @RequestParam String keyword
    ) {

        return ResponseEntity.ok(
                jobSearchService.searchJobs(keyword)
        );
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobSearchResponse> getJobById(
            @PathVariable Long jobId
    ) {

        return ResponseEntity.ok(
                jobSearchService.getJobById(jobId)
        );
    }
}