package com.rbme.apis.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectEnquiryRequest {

    @NotNull(message = "Company is required")
    private Long companyId;

    @NotBlank(message = "Category is required")
    @Size(max = 100)
    private String category;

    @Size(max = 150)
    private String machine;

    @NotBlank(message = "Project type is required")
    @Size(max = 100)
    private String projectType;

    @Size(max = 100)
    private String capacity;

    @Size(max = 100)
    private String product;

    @Size(max = 100)
    private String automation;

    @Size(max = 100)
    private String budget;

    private String message;

    @NotBlank(message = "Name is required")
    @Size(max = 150)
    private String name;

    @Size(max = 200)
    private String businessName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 30)
    private String phone;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String state;
}