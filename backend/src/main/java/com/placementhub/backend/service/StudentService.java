package com.placementhub.backend.service;

import com.placementhub.backend.dto.StudentProfileRequest;
import com.placementhub.backend.dto.StudentProfileResponse;
import com.placementhub.backend.entity.Student;
import com.placementhub.backend.entity.User;
import com.placementhub.backend.repository.StudentRepository;
import com.placementhub.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    public StudentService(
            StudentRepository studentRepository,
            UserRepository userRepository
    ) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }



    public StudentProfileResponse createProfile(
            String email,
            StudentProfileRequest request
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        if (studentRepository.existsByUserId(
                user.getId()
        )) {

            throw new RuntimeException(
                    "Student profile already exists"
            );
        }

        Student student = new Student();

        student.setUser(user);
        student.setFullName(request.getFullName());
        student.setPhone(request.getPhone());
        student.setCollege(request.getCollege());
        student.setBranch(request.getBranch());
        student.setCgpa(request.getCgpa());
        student.setGraduationYear(
                request.getGraduationYear()
        );
        student.setResumeUrl(
                request.getResumeUrl()
        );

        Student savedStudent =
                studentRepository.save(student);

        return convertToResponse(savedStudent);
    }




    public StudentProfileResponse getProfile(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Student student =
                studentRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student profile not found"
                                )
                        );

        return convertToResponse(student);
    }



    public StudentProfileResponse updateProfile(
            String email,
            StudentProfileRequest request
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Student student =
                studentRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student profile not found"
                                )
                        );

        student.setFullName(request.getFullName());
        student.setPhone(request.getPhone());
        student.setCollege(request.getCollege());
        student.setBranch(request.getBranch());
        student.setCgpa(request.getCgpa());
        student.setGraduationYear(
                request.getGraduationYear()
        );
        student.setResumeUrl(
                request.getResumeUrl()
        );

        Student updatedStudent =
                studentRepository.save(student);

        return convertToResponse(updatedStudent);
    }




    private StudentProfileResponse convertToResponse(
            Student student
    ) {

        return new StudentProfileResponse(
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
}