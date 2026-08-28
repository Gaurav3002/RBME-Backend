package com.rbme.apis.dto.admin;

import lombok.Data;

@Data
public class AdminUpdateRequest {

    private String fullName;

    private String username;

    private String email;

    private Boolean active;
}