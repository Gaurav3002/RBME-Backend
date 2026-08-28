package com.rbme.apis.services;

import com.rbme.apis.dto.admin.ProductType.ProductTypeRequest;
import com.rbme.apis.dto.admin.ProductType.ProductTypeResponse;
import com.rbme.apis.entity.ProductType;

import java.util.List;

public interface ProductTypeService {

    ProductTypeResponse createProductType(ProductTypeRequest productTypeRequest);
    ProductTypeResponse updateProductType(Long id, ProductTypeRequest productTypeRequest);
    void delete(Long id);
    ProductTypeResponse getById(Long id);
    List<ProductTypeResponse> getAll();
    List<ProductTypeResponse> getByCategory(Long categoryId);
}
