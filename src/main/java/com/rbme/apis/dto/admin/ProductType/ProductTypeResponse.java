package com.rbme.apis.dto.admin.ProductType;

import lombok.Data;

@Data
public class ProductTypeResponse {

    private Long id;
    private String companyName;
    private Long companyId;
    private Long categoryId;
    private String categoryName;
    private String productTypeName;
    private Boolean active;
}
