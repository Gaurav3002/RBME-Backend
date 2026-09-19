package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleRequestDTO {

    private String name;

    private String description;

    private Boolean active;

    private Set<Long> permissionIds;
}