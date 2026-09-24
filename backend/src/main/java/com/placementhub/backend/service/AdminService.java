package com.placementhub.backend.service;

import com.placementhub.backend.dto.AdminCompanyResponse;
import com.placementhub.backend.dto.AdminJobResponse;
import com.placementhub.backend.dto.AdminRecruiterResponse;
import com.placementhub.backend.dto.AdminStudentResponse;
import com.placementhub.backend.dto.AdminUserResponse;
import com.placementhub.backend.entity.Application;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final RecruiterRepository recruiterRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    public AdminService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            RecruiterRepository recruiterRepository,
            CompanyRepository companyRepository,
            JobRepository jobRepository,
            ApplicationRepository applicationRepository
    ) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.recruiterRepository = recruiterRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<AdminUserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::convertToUserResponse)
                .toList();
    }

    public AdminUserResponse getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return convertToUserResponse(user);
    }

    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        userRepository.delete(user);
    }
    public List<AdminStudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::convertToStudentResponse)
                .toList();
    }

    public List<AdminRecruiterResponse> getAllRecruiters() {

        return recruiterRepository.findAll()
                .stream()
                .map(this::convertToRecruiterResponse)
                .toList();
    }

    public List<AdminCompanyResponse> getAllCompanies() {

        return companyRepository.findAll()
                .stream()
                .map(this::convertToCompanyResponse)
                .toList();
    }

    public List<AdminJobResponse> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::convertToJobResponse)
                .toList();
    }


    public void deleteJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );

        List<Application> applications =
                applicationRepository.findByJobId(jobId);

        applicationRepository.deleteAll(applications);

        jobRepository.delete(job);
    }

    private AdminUserResponse convertToUserResponse(User user) {

        return new AdminUserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    private AdminStudentResponse convertToStudentResponse(
            Student student
    ) {

        return new AdminStudentResponse(
                student.getId(),
                student.getUser().getId(),
                student.getUser().getEmail(),
                student.getFullName(),
                student.getPhone(),
                student.getCollege(),
                student.getBranch(),
                student.getCgpa(),
                student.getGraduationYear(),
                student.getResumeUrl()
        );
    }

    private AdminRecruiterResponse convertToRecruiterResponse(
            Recruiter recruiter
    ) {

        return new AdminRecruiterResponse(
                recruiter.getId(),
                recruiter.getUser().getId(),
                recruiter.getUser().getEmail(),
                recruiter.getFullName(),
                recruiter.getPhone(),
                recruiter.getDesignation()
        );
    }

    private AdminCompanyResponse convertToCompanyResponse(
            Company company
    ) {

        return new AdminCompanyResponse(
                company.getId(),
                company.getRecruiter().getId(),
                company.getRecruiter().getFullName(),
                company.getCompanyName(),
                company.getDescription(),
                company.getWebsite(),
                company.getLocation(),
                company.getIndustry()
        );
    }

    private AdminJobResponse convertToJobResponse(
            Job job
    ) {

        return new AdminJobResponse(
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