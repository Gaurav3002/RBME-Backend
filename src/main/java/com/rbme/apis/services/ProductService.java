package com.rbme.apis.services;

import com.rbme.apis.dto.admin.Product.ProductRequest;
import com.rbme.apis.dto.admin.Product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);
    void delete(Long id);

    ProductResponse getById(Long id);
    List<ProductResponse> getAll();

    List<ProductResponse> getByCompany(Long companyId);
    List<ProductResponse> getByCategory(Long categoryId);

    List<ProductResponse> getByProductType(Long typeId);

    List<ProductResponse> getActiveProducts();

    List<ProductResponse> getFeaturedProducts();

}
