package com.placementhub.backend.dto;

import com.placementhub.backend.entity.User.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private Long userId;

    private String email;

    private Role role;

    private String token;
}