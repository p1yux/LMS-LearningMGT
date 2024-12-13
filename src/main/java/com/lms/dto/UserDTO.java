package com.lms.dto;

import com.lms.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private User.UserRole role;
} 