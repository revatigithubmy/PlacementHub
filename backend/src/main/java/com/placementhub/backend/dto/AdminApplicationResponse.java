package com.placementhub.backend.dto;

import com.placementhub.backend.entity.Application.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminApplicationResponse {

    private Long applicationId;

    private Long studentId;
    private String studentName;
    private String studentEmail;

    private Long jobId;
    private String jobTitle;

    private Long companyId;
    private String companyName;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}