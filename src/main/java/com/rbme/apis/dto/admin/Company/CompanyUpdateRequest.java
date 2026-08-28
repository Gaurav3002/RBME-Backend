package com.rbme.apis.dto.admin.Company;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CompanyUpdateRequest {

    private String name;

    private MultipartFile logo;

    private MultipartFile banner;

    private String description;

    private String website;

    private Boolean active;

}