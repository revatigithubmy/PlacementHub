package com.placementhub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentProfileResponse {

    private Long studentId;

    private Long userId;

    private String email;

    private String fullName;

    private String phone;

    private String college;

    private String branch;

    private Double cgpa;

    private Integer graduationYear;

    private String resumeUrl;
}