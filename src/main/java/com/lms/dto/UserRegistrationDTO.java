package com.lms.dto;

import com.lms.model.User.UserRole;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private UserRole role;
} 