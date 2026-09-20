package com.placementhub.backend.dto;

import com.placementhub.backend.entity.JobType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class JobSearchResponse {

    private Long jobId;

    private Long companyId;

    private String companyName;

    private String title;

    private String location;

    private JobType jobType;

    private Double salary;

    private Integer experienceRequired;

    private String skills;

    private LocalDate applicationDeadline;
}