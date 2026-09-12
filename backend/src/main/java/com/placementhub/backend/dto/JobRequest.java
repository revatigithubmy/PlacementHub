package com.placementhub.backend.dto;

import com.placementhub.backend.entity.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobRequest {

    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Job description is required")
    private String description;

    private String location;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    private Double salary;

    private Integer experienceRequired;

    private String skills;

    private LocalDate applicationDeadline;
}