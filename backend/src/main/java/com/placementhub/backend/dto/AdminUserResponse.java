package com.placementhub.backend.dto;

import com.placementhub.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminUserResponse {

    private Long id;
    private String email;
    private User.Role role;
}