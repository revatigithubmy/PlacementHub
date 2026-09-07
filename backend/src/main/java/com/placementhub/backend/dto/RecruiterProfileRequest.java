package com.placementhub.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruiterProfileRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String phone;

    private String designation;
}