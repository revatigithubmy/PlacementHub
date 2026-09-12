package com.placementhub.backend.service;

import com.placementhub.backend.dto.JobRequest;
import com.placementhub.backend.dto.JobResponse;
import com.placementhub.backend.entity.Company;
import com.placementhub.backend.entity.Job;
import com.placementhub.backend.entity.User;
import com.placementhub.backend.repository.CompanyRepository;
import com.placementhub.backend.repository.JobRepository;
import com.placementhub.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public JobService(
            JobRepository jobRepository,
            CompanyRepository companyRepository,
            UserRepository userRepository
    ) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public JobResponse createJob(
            String email,
            JobRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Company company = companyRepository
                .findByRecruiterUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));

        Job job = new Job();

        job.setCompany(company);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setSalary(request.getSalary());
        job.setExperienceRequired(
                request.getExperienceRequired()
        );
        job.setSkills(request.getSkills());
        job.setApplicationDeadline(
                request.getApplicationDeadline()
        );
        job.setCreatedAt(LocalDateTime.now());

        Job savedJob = jobRepository.save(job);

        return convertToResponse(savedJob);
    }

    public List<JobResponse> getMyJobs(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Company company = companyRepository
                .findByRecruiterUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));

        return jobRepository
                .findByCompany(company)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public JobResponse getJob(
            String email,
            Long jobId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Company company = companyRepository
                .findByRecruiterUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));

        Job job = jobRepository
                .findByIdAndCompanyId(
                        jobId,
                        company.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        return convertToResponse(job);
    }

    public JobResponse updateJob(
            String email,
            Long jobId,
            JobRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Company company = companyRepository
                .findByRecruiterUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));

        Job job = jobRepository
                .findByIdAndCompanyId(
                        jobId,
                        company.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setSalary(request.getSalary());
        job.setExperienceRequired(
                request.getExperienceRequired()
        );
        job.setSkills(request.getSkills());
        job.setApplicationDeadline(
                request.getApplicationDeadline()
        );

        Job updatedJob = jobRepository.save(job);

        return convertToResponse(updatedJob);
    }

    public void deleteJob(
            String email,
            Long jobId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Company company = companyRepository
                .findByRecruiterUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));

        Job job = jobRepository
                .findByIdAndCompanyId(
                        jobId,
                        company.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        jobRepository.delete(job);
    }

    private JobResponse convertToResponse(Job job) {

        return new JobResponse(
                job.getId(),
                job.getCompany().getId(),
                job.getCompany().getCompanyName(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getJobType(),
                job.getSalary(),
                job.getExperienceRequired(),
                job.getSkills(),
                job.getApplicationDeadline(),
                job.getCreatedAt()
        );
    }
}