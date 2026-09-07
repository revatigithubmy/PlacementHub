package com.placementhub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecruiterProfileResponse {

    private Long recruiterId;

    private Long userId;

    private String email;

    private String fullName;

    private String phone;

    private String designation;
}