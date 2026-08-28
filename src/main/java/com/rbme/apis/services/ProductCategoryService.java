package com.rbme.apis.services;

import com.rbme.apis.dto.admin.category.ProductCategoryCreateRequest;
import com.rbme.apis.dto.admin.category.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {

    ProductCategoryResponse create(ProductCategoryCreateRequest request);
    ProductCategoryResponse update(Long id, ProductCategoryCreateRequest request);
    void delete(Long id);
    ProductCategoryResponse getCategoryById(Long id);
    List<ProductCategoryResponse> getAllCategories();
    List<ProductCategoryResponse> getByCompany(Long companyId);
    void updateStatus(Long id, Boolean active);

}
