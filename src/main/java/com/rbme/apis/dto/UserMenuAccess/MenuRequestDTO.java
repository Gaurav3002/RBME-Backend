package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDTO {

    private String code;

    private String name;

    private String icon;

    private String route;

    private Integer displayOrder;

    private Boolean active;

    private Long parentId;

    private Long permissionId;
}