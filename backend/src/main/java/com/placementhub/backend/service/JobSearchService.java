package com.placementhub.backend.service;

import com.placementhub.backend.dto.JobSearchResponse;
import com.placementhub.backend.entity.Job;
import com.placementhub.backend.exception.ResourceNotFoundException;
import com.placementhub.backend.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSearchService {

    private final JobRepository jobRepository;

    public JobSearchService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobSearchResponse> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<JobSearchResponse> searchJobs(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllJobs();
        }

        return jobRepository
                .findByTitleContainingIgnoreCaseOrSkillsContainingIgnoreCase(
                        keyword,
                        keyword
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public JobSearchResponse getJobById(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        return convertToResponse(job);
    }

    private JobSearchResponse convertToResponse(Job job) {

        return new JobSearchResponse(
                job.getId(),
                job.getCompany().getId(),
                job.getCompany().getCompanyName(),
                job.getTitle(),
                job.getLocation(),
                job.getJobType(),
                job.getSalary(),
                job.getExperienceRequired(),
                job.getSkills(),
                job.getApplicationDeadline()
        );
    }
}