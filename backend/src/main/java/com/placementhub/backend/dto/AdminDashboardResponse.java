package com.placementhub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDashboardResponse {

    private long totalUsers;
    private long totalStudents;
    private long totalRecruiters;
    private long totalCompanies;
    private long totalJobs;
    private long totalApplications;
    private long selectedStudents;
}