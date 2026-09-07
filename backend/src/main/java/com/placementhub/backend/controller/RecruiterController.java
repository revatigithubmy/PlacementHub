package com.placementhub.backend.controller;

import com.placementhub.backend.dto.RecruiterProfileRequest;
import com.placementhub.backend.dto.RecruiterProfileResponse;
import com.placementhub.backend.service.RecruiterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    private final RecruiterService recruiterService;

    public RecruiterController(
            RecruiterService recruiterService
    ) {
        this.recruiterService = recruiterService;
    }

    @PostMapping("/profile")
    public ResponseEntity<RecruiterProfileResponse> createProfile(
            Authentication authentication,
            @Valid @RequestBody RecruiterProfileRequest request
    ) {

        String email = authentication.getName();

        RecruiterProfileResponse response =
                recruiterService.createProfile(email, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<RecruiterProfileResponse> getProfile(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                recruiterService.getProfile(email)
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<RecruiterProfileResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody RecruiterProfileRequest request
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                recruiterService.updateProfile(email, request)
        );
    }
}