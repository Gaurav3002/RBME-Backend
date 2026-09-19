package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String fullName;

    private String username;

    private String email;

    private String password;

    private Long roleId;

    private Boolean active = true;
}
