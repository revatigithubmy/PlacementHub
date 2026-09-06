package com.placementhub.backend.controller;

import com.placementhub.backend.dto.StudentProfileRequest;
import com.placementhub.backend.dto.StudentProfileResponse;
import com.placementhub.backend.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(
            StudentService studentService
    ) {
        this.studentService = studentService;
    }


    @PostMapping("/profile")
    public ResponseEntity<StudentProfileResponse>
    createProfile(
            Authentication authentication,
            @Valid @RequestBody StudentProfileRequest request
    ) {

        String email =
                authentication.getName();

        StudentProfileResponse response =
                studentService.createProfile(
                        email,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }




    @GetMapping("/profile")
    public ResponseEntity<StudentProfileResponse>
    getProfile(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                studentService.getProfile(email)
        );
    }




    @PutMapping("/profile")
    public ResponseEntity<StudentProfileResponse>
    updateProfile(
            Authentication authentication,
            @Valid @RequestBody StudentProfileRequest request
    ) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                studentService.updateProfile(
                        email,
                        request
                )
        );
    }
}