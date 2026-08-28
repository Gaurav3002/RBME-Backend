package com.rbme.apis.services;

import com.rbme.apis.dto.admin.ProductSpecification.ProductSpecificationRequest;
import com.rbme.apis.dto.admin.ProductSpecification.ProductSpecificationResponse;

import java.util.List;

public interface ProductSpecificationService {

    ProductSpecificationResponse createSpecification(ProductSpecificationRequest request);
    ProductSpecificationResponse updateSpecification(Long id, ProductSpecificationRequest request);
    ProductSpecificationResponse getById(Long id);
    void delete(Long id);
    List<ProductSpecificationResponse> getAll();
    List<ProductSpecificationResponse> getByProductId(Long productId);
}
