package com.placementhub.backend.service;

import com.placementhub.backend.dto.RecruiterProfileRequest;
import com.placementhub.backend.dto.RecruiterProfileResponse;
import com.placementhub.backend.entity.Recruiter;
import com.placementhub.backend.entity.User;
import com.placementhub.backend.repository.RecruiterRepository;
import com.placementhub.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    public RecruiterService(
            RecruiterRepository recruiterRepository,
            UserRepository userRepository
    ) {
        this.recruiterRepository = recruiterRepository;
        this.userRepository = userRepository;
    }

    public RecruiterProfileResponse createProfile(
            String email,
            RecruiterProfileRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (recruiterRepository.existsByUserId(user.getId())) {
            throw new RuntimeException(
                    "Recruiter profile already exists"
            );
        }

        Recruiter recruiter = new Recruiter();

        recruiter.setUser(user);
        recruiter.setFullName(request.getFullName());
        recruiter.setPhone(request.getPhone());
        recruiter.setDesignation(request.getDesignation());

        Recruiter savedRecruiter =
                recruiterRepository.save(recruiter);

        return convertToResponse(savedRecruiter);
    }

    public RecruiterProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Recruiter recruiter =
                recruiterRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter profile not found"
                                ));

        return convertToResponse(recruiter);
    }

    public RecruiterProfileResponse updateProfile(
            String email,
            RecruiterProfileRequest request
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

        recruiter.setFullName(request.getFullName());
        recruiter.setPhone(request.getPhone());
        recruiter.setDesignation(request.getDesignation());

        Recruiter updatedRecruiter =
                recruiterRepository.save(recruiter);

        return convertToResponse(updatedRecruiter);
    }

    private RecruiterProfileResponse convertToResponse(
            Recruiter recruiter
    ) {

        return new RecruiterProfileResponse(
                recruiter.getId(),
                recruiter.getUser().getId(),
                recruiter.getUser().getEmail(),
                recruiter.getFullName(),
                recruiter.getPhone(),
                recruiter.getDesignation()
        );
    }
}