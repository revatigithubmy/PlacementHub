package com.placementhub.backend.dto;

import com.placementhub.backend.entity.Application.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationStatusRequest {

    private ApplicationStatus status;
}