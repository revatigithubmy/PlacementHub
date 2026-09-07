package com.placementhub.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "recruiter_id", nullable = false, unique = true)
    private Recruiter recruiter;

    @Column(nullable = false)
    private String companyName;

    @Column(length = 1000)
    private String description;

    private String website;

    private String location;

    private String industry;
}