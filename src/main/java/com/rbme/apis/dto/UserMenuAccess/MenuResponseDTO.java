package com.rbme.apis.dto.UserMenuAccess;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuResponseDTO {

    private Long id;

    private String code;

    private String name;

    private String icon;

    private String route;

    private Integer displayOrder;

    private Boolean active;

    private Long parentId;

    private Long permissionId;

    private List<MenuResponseDTO> children = new ArrayList<>();
}