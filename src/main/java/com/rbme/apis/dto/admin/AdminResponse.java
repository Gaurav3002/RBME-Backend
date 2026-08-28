package com.rbme.apis.dto.admin;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AdminResponse {

    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String role;

    private Boolean active;

    private LocalDateTime lastLogin;

    private LocalDateTime createdAt;
}