package com.placementhub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResumeResponse {

    private String message;
    private String fileName;
    private String fileUrl;
}