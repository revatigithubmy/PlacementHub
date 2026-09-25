package com.placementhub.backend.controller;

import com.placementhub.backend.dto.ResumeResponse;
import com.placementhub.backend.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/student/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<ResumeResponse> uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {

        ResumeResponse response =
                resumeService.uploadResume(
                        file,
                        authentication.getName()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResumeResponse> getMyResume(
            Authentication authentication
    ) {

        ResumeResponse response =
                resumeService.getMyResume(
                        authentication.getName()
                );

        return ResponseEntity.ok(response);
    }
    @PutMapping
    public ResponseEntity<ResumeResponse> updateResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {

        ResumeResponse response =
                resumeService.updateResume(
                        file,
                        authentication.getName()
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteResume(
            Authentication authentication
    ) {

        resumeService.deleteResume(
                authentication.getName()
        );

        return ResponseEntity.ok(
                "Resume deleted successfully"
        );
    }
}