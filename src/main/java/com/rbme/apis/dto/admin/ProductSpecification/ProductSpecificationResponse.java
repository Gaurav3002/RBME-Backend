package com.rbme.apis.dto.admin.ProductSpecification;

import lombok.Data;

@Data
public class ProductSpecificationResponse {

    private Long id;

    private Long productId;

    private String productName;

    private Long companyId;

    private String companyName;

    private Long categoryId;

    private String categoryName;

    private Long productTypeId;

    private String productTypeName;

    private String specificationName;

    private String specificationValue;
}