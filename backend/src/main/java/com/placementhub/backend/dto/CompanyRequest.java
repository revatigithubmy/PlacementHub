package com.placementhub.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    private String description;

    private String website;

    private String location;

    private String industry;
}