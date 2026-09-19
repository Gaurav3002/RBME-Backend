package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean active;

    private Set<String> permissions;
}