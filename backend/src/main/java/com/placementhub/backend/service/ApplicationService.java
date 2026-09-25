package com.placementhub.backend.service;

import com.placementhub.backend.dto.ApplicationResponse;
import com.placementhub.backend.dto.ApplicationStatusRequest;
import com.placementhub.backend.entity.Application;
import com.placementhub.backend.entity.Application.ApplicationStatus;
import com.placementhub.backend.entity.Company;
import com.placementhub.backend.entity.Job;
import com.placementhub.backend.entity.Recruiter;
import com.placementhub.backend.entity.Student;
import com.placementhub.backend.entity.User;
import com.placementhub.backend.repository.ApplicationRepository;
import com.placementhub.backend.repository.CompanyRepository;
import com.placementhub.backend.repository.JobRepository;
import com.placementhub.backend.repository.RecruiterRepository;
import com.placementhub.backend.repository.StudentRepository;
import com.placementhub.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final RecruiterRepository recruiterRepository;
    private final CompanyRepository companyRepository;
    private final NotificationService notificationService;
    public ApplicationService(
            ApplicationRepository applicationRepository,
            StudentRepository studentRepository,
            JobRepository jobRepository,
            UserRepository userRepository,
            RecruiterRepository recruiterRepository,
            CompanyRepository companyRepository,
            NotificationService notificationService
    ) {
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.recruiterRepository = recruiterRepository;
        this.companyRepository = companyRepository;
        this.notificationService = notificationService;
    }

    public ApplicationResponse applyForJob(
            Long jobId,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        Student student = studentRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found"
                        ));

        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job not found"
                        ));

        if (applicationRepository
                .existsByStudentIdAndJobId(
                        student.getId(),
                        jobId
                )) {

            throw new RuntimeException(
                    "You have already applied for this job"
            );
        }

        Application application = new Application();

        application.setStudent(student);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setAppliedAt(LocalDateTime.now());

        Application savedApplication =
                applicationRepository.save(application);
        Long recruiterUserId =
                savedApplication
                        .getJob()
                        .getCompany()
                        .getRecruiter()
                        .getUser()
                        .getId();

        notificationService.createNotification(
                recruiterUserId,
                "New application received for job: "
                        + savedApplication.getJob().getTitle()
        );

        return convertToResponse(savedApplication);
    }

    public List<ApplicationResponse> getMyApplications(
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        Student student = studentRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found"
                        ));

        return applicationRepository
                .findByStudentId(student.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ApplicationResponse getMyApplication(
            Long applicationId,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        Student student = studentRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found"
                        ));

        Application application =
                applicationRepository
                        .findByIdAndStudentId(
                                applicationId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                ));

        return convertToResponse(application);
    }

    public void withdrawApplication(
            Long applicationId,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        Student student = studentRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found"
                        ));

        Application application =
                applicationRepository
                        .findByIdAndStudentId(
                                applicationId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                ));

        applicationRepository.delete(application);
    }

    public List<ApplicationResponse> getApplicationsForJob(
            Long jobId,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        Recruiter recruiter = recruiterRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"
                        ));

        Company company = companyRepository
                .findByRecruiter(recruiter)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company profile not found"
                        ));

        Job job = jobRepository
                .findByIdAndCompanyId(
                        jobId,
                        company.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job not found or does not belong to you"
                        ));

        return applicationRepository
                .findByJobId(job.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ApplicationResponse getRecruiterApplication(
            Long applicationId,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        Recruiter recruiter = recruiterRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"
                        ));

        Company company = companyRepository
                .findByRecruiter(recruiter)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company profile not found"
                        ));

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found"
                        ));


        if (!application.getJob()
                .getCompany()
                .getId()
                .equals(company.getId())) {

            throw new RuntimeException(
                    "You are not authorized to view this application"
            );
        }

        return convertToResponse(application);
    }

    public ApplicationResponse updateApplicationStatus(
            Long applicationId,
            ApplicationStatusRequest request,
            Authentication authentication
    ) {


        User user = getLoggedInUser(authentication);

        Recruiter recruiter = recruiterRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"
                        ));

        Company company = companyRepository
                .findByRecruiter(recruiter)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company profile not found"
                        ));

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found"
                        ));


        if (!application.getJob()
                .getCompany()
                .getId()
                .equals(company.getId())) {

            throw new RuntimeException(
                    "You are not authorized to update this application"
            );
        }

        if (request == null || request.getStatus() == null) {

            throw new RuntimeException(
                    "Application status is required"
            );
        }

        application.setStatus(request.getStatus());

        Application updatedApplication =
                applicationRepository.save(application);
        Long studentUserId =
                updatedApplication
                        .getStudent()
                        .getUser()
                        .getId();

        notificationService.createNotification(
                studentUserId,
                "Your application for "
                        + updatedApplication.getJob().getTitle()
                        + " has been updated to "
                        + updatedApplication.getStatus()
        );

        return convertToResponse(updatedApplication);
    }

    private User getLoggedInUser(
            Authentication authentication
    ) {
        String email = authentication.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));
    }

    private ApplicationResponse convertToResponse(
            Application application
    ) {
        return new ApplicationResponse(
                application.getId(),
                application.getStudent().getId(),
                application.getStudent().getFullName(),
                application.getJob().getId(),
                application.getJob().getTitle(),
                application.getJob()
                        .getCompany()
                        .getCompanyName(),

                application.getStatus(),
                application.getAppliedAt()
        );
    }
}