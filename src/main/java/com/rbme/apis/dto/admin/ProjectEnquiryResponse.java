package com.rbme.apis.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectEnquiryResponse {

    private Long id;

    private Long companyId;
    private String companyName;

    private String category;
    private String machine;

    private String projectType;
    private String capacity;
    private String product;
    private String automation;
    private String budget;
    private String message;

    private String name;
    private String businessName;
    private String email;
    private String phone;
    private String city;
    private String state;

    private String status;

    private LocalDateTime createdAt;



}
