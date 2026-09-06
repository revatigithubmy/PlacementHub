package com.placementhub.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentProfileRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String phone;

    private String college;

    private String branch;

    @Min(value = 0, message = "CGPA cannot be negative")
    @Max(value = 10, message = "CGPA cannot be greater than 10")
    private Double cgpa;

    private Integer graduationYear;

    private String resumeUrl;
}