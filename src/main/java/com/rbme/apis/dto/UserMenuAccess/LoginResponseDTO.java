package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class LoginResponseDTO {

    private String token;

    private Long userId;

    private String fullName;

    private String username;

    private String role;

    private Set<String> permissions;
}
