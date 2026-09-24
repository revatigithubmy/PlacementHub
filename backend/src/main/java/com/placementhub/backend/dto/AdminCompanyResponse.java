package com.placementhub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminCompanyResponse {

    private Long companyId;
    private Long recruiterId;
    private String recruiterName;
    private String companyName;
    private String description;
    private String website;
    private String location;
    private String industry;
}