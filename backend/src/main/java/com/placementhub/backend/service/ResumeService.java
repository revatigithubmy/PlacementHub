package com.placementhub.backend.service;

import com.placementhub.backend.dto.ResumeResponse;
import com.placementhub.backend.entity.Student;
import com.placementhub.backend.entity.User;
import com.placementhub.backend.repository.StudentRepository;
import com.placementhub.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ResumeService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    private final Path uploadPath =
            Paths.get("uploads/resumes");

    public ResumeService(
            StudentRepository studentRepository,
            UserRepository userRepository
    ) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public ResumeResponse uploadResume(
            MultipartFile file,
            String email
    ) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please select a resume file");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null ||
                !originalFileName.toLowerCase().endsWith(".pdf")) {

            throw new RuntimeException("Only PDF files are allowed");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        try {

            Files.createDirectories(uploadPath);

            String fileName =
                    "resume_" +
                            student.getId() +
                            "_" +
                            System.currentTimeMillis() +
                            ".pdf";

            Path filePath = uploadPath.resolve(fileName);

            Files.write(filePath, file.getBytes());

            String fileUrl = "/uploads/resumes/" + fileName;

            student.setResumeUrl(fileUrl);

            studentRepository.save(student);

            return new ResumeResponse(
                    "Resume uploaded successfully",
                    fileName,
                    fileUrl
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload resume"
            );
        }
    }
    public ResumeResponse getMyResume(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        if (student.getResumeUrl() == null ||
                student.getResumeUrl().isBlank()) {

            throw new RuntimeException("Resume not found");
        }

        String fileUrl = student.getResumeUrl();

        String fileName = Paths.get(fileUrl)
                .getFileName()
                .toString();

        return new ResumeResponse(
                "Resume found",
                fileName,
                fileUrl
        );
    }
    public ResumeResponse updateResume(
            MultipartFile file,
            String email
    ) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please select a resume file");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null ||
                !originalFileName.toLowerCase().endsWith(".pdf")) {

            throw new RuntimeException("Only PDF files are allowed");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        try {

            Files.createDirectories(uploadPath);

            if (student.getResumeUrl() != null &&
                    !student.getResumeUrl().isBlank()) {

                String oldFileName =
                        Paths.get(student.getResumeUrl())
                                .getFileName()
                                .toString();

                Path oldFilePath =
                        uploadPath.resolve(oldFileName);

                Files.deleteIfExists(oldFilePath);
            }

            String fileName =
                    "resume_" +
                            student.getId() +
                            "_" +
                            System.currentTimeMillis() +
                            ".pdf";

            Path filePath =
                    uploadPath.resolve(fileName);

            Files.write(filePath, file.getBytes());

            String fileUrl =
                    "/uploads/resumes/" + fileName;

            student.setResumeUrl(fileUrl);

            studentRepository.save(student);

            return new ResumeResponse(
                    "Resume updated successfully",
                    fileName,
                    fileUrl
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to update resume"
            );
        }
    }
    public void deleteResume(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        if (student.getResumeUrl() == null ||
                student.getResumeUrl().isBlank()) {

            throw new RuntimeException("Resume not found");
        }

        try {

            String fileName =
                    Paths.get(student.getResumeUrl())
                            .getFileName()
                            .toString();

            Path filePath =
                    uploadPath.resolve(fileName);

            Files.deleteIfExists(filePath);

            student.setResumeUrl(null);

            studentRepository.save(student);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to delete resume"
            );
        }
    }
}