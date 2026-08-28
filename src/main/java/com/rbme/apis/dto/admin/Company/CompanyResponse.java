package com.rbme.apis.dto.admin.Company;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyResponse {

    private Long id;

    private String name;

    private String logo;

    private String banner;

    private String description;

    private String website;

    private Boolean active;

}