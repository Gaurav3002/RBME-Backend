package com.rbme.apis.dto.admin.category;

import lombok.Data;

@Data
public class ProductCategoryResponse {

    private Long id;
    private String name;
    private Boolean active;
    private Long companyId;
    private String companyName;
}
