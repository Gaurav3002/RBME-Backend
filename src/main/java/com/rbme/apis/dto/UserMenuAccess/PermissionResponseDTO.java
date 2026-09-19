package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponseDTO {
    private Long id;

    private String code;

    private String name;

    private String description;

    private Boolean active;
}
