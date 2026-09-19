package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequestDTO {

    private String code;

    private String name;

    private String description;

    private Boolean active;
}