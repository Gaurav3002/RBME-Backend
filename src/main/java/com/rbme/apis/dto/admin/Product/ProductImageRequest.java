package com.rbme.apis.dto.admin.Product;

import lombok.Data;

@Data
public class ProductImageRequest {

    private Long id;

    private String imageUrl;

    private Integer sortOrder;
}