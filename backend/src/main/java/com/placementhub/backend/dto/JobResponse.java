package com.placementhub.backend.dto;

import com.placementhub.backend.entity.JobType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class JobResponse {

    private Long jobId;

    private Long companyId;

    private String companyName;

    private String title;

    private String description;

    private String location;

    private JobType jobType;

    private Double salary;

    private Integer experienceRequired;

    private String skills;

    private LocalDate applicationDeadline;

    private LocalDateTime createdAt;
}