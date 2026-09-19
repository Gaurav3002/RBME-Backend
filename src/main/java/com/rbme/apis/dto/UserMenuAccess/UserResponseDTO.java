package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;

    private String fullName;

    private String username;

    private String email;

    private Long roleId;

    private String roleName;

    private Boolean active;

    private LocalDateTime lastLogin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
