package com.placementhub.backend.dto;

import com.placementhub.backend.entity.Application.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationResponse {

    private Long applicationId;

    private Long studentId;

    private String studentName;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}