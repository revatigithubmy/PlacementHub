package com.placementhub.backend.service;

import com.placementhub.backend.dto.CompanyRequest;
import com.placementhub.backend.dto.CompanyResponse;
import com.placementhub.backend.entity.Company;
import com.placementhub.backend.entity.Recruiter;
import com.placementhub.backend.entity.User;
import com.placementhub.backend.repository.CompanyRepository;
import com.placementhub.backend.repository.RecruiterRepository;
import com.placementhub.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    public CompanyService(
            CompanyRepository companyRepository,
            RecruiterRepository recruiterRepository,
            UserRepository userRepository
    ) {
        this.companyRepository = companyRepository;
        this.recruiterRepository = recruiterRepository;
        this.userRepository = userRepository;
    }

    public CompanyResponse createCompany(
            String email,
            CompanyRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Recruiter recruiter =
                recruiterRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter profile not found"
                                ));

        if (companyRepository
                .existsByRecruiterId(recruiter.getId())) {

            throw new RuntimeException(
                    "Company already exists"
            );
        }

        Company company = new Company();

        company.setRecruiter(recruiter);
        company.setCompanyName(request.getCompanyName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setIndustry(request.getIndustry());

        Company savedCompany =
                companyRepository.save(company);

        return convertToResponse(savedCompany);
    }

    public CompanyResponse getCompany(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Recruiter recruiter =
                recruiterRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter profile not found"
                                ));

        Company company =
                companyRepository.findByRecruiter(recruiter)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found"
                                ));

        return convertToResponse(company);
    }

    public CompanyResponse updateCompany(
            String email,
            CompanyRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Recruiter recruiter =
                recruiterRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter profile not found"
                                ));

        Company company =
                companyRepository.findByRecruiter(recruiter)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found"
                                ));

        company.setCompanyName(request.getCompanyName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setIndustry(request.getIndustry());

        Company updatedCompany =
                companyRepository.save(company);

        return convertToResponse(updatedCompany);
    }

    public void deleteCompany(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Recruiter recruiter =
                recruiterRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter profile not found"
                                ));

        Company company =
                companyRepository.findByRecruiter(recruiter)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found"
                                ));

        companyRepository.delete(company);
    }

    private CompanyResponse convertToResponse(
            Company company
    ) {

        return new CompanyResponse(
                company.getId(),
                company.getRecruiter().getId(),
                company.getCompanyName(),
                company.getDescription(),
                company.getWebsite(),
                company.getLocation(),
                company.getIndustry()
        );
    }
}