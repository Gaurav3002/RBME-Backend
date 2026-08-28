package com.rbme.apis.dto.admin.Company;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CompanyCreateRequest {

    @NotBlank(message = "Company name is required")
    private String name;

    private MultipartFile logo;

    private MultipartFile banner;

    private String description;

    private String website;

    private Boolean active = true;

}