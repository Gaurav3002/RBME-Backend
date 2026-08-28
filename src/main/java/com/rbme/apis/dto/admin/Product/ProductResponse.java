package com.rbme.apis.dto.admin.Product;

import com.rbme.apis.dto.admin.ProductSpecification.ProductSpecificationResponse;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private Long id;

    private Long companyId;
    private String companyName;
    private String companyBanner;

    private Long categoryId;
    private String categoryName;

    private Long productTypeId;
    private String productTypeName;

    private String modelNo;

    private String title;

    private String description;

    private String thumbnail;

    private String youtubeUrl;

    private Boolean featured;

    private Boolean active;

    private List<ProductImageResponse> images;
    private List<ProductSpecificationResponse> specifications;
}