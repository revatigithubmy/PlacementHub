package com.placementhub.backend.controller;

import com.placementhub.backend.dto.CompanyRequest;
import com.placementhub.backend.dto.CompanyResponse;
import com.placementhub.backend.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(
            CompanyService companyService
    ) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            Authentication authentication,
            @Valid @RequestBody CompanyRequest request
    ) {

        String email = authentication.getName();

        CompanyResponse response =
                companyService.createCompany(
                        email,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CompanyResponse> getCompany(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                companyService.getCompany(email)
        );
    }

    @PutMapping
    public ResponseEntity<CompanyResponse> updateCompany(
            Authentication authentication,
            @Valid @RequestBody CompanyRequest request
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                companyService.updateCompany(
                        email,
                        request
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCompany(
            Authentication authentication
    ) {

        String email = authentication.getName();

        companyService.deleteCompany(email);

        return ResponseEntity.noContent().build();
    }
}