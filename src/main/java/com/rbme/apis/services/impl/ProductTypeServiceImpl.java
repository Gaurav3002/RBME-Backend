package com.rbme.apis.services.impl;

import com.rbme.apis.dto.admin.ProductType.ProductTypeRequest;
import com.rbme.apis.dto.admin.ProductType.ProductTypeResponse;
import com.rbme.apis.entity.ProductCategory;
import com.rbme.apis.entity.ProductType;
import com.rbme.apis.exception.DuplicateResourceException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.CompanyRepository;
import com.rbme.apis.repository.ProductCategoryRepository;
import com.rbme.apis.repository.ProductTypeRepository;
import com.rbme.apis.services.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductCategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;

    @Override
    public ProductTypeResponse createProductType(ProductTypeRequest productTypeRequest) {
        ProductCategory category = categoryRepository.findById(productTypeRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Exist With This Name!"));
        if(productTypeRepository.existsByNameIgnoreCase(productTypeRequest.getName()) ||
        productTypeRepository.existsByNameIgnoreCaseAndCategoryId(productTypeRequest.getName(), productTypeRequest.getCategoryId())){
            throw new DuplicateResourceException("Product Type Already Exist!");
        }
        ProductType type = new ProductType();
        type.setName(productTypeRequest.getName());
        type.setCategory(category);
        type.setActive(productTypeRequest.getActive());
        productTypeRepository.save(type);
        return map(type);
    }

    @Override
    public ProductTypeResponse updateProductType(Long id, ProductTypeRequest productTypeRequest) {
        ProductType productType = productTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductType Not found!"));
        ProductCategory category = categoryRepository.findById(productTypeRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
        productType.setName(productTypeRequest.getName());
        productType.setActive(productTypeRequest.getActive());
        productType.setCategory(category);
        productTypeRepository.save(productType);
        return map(productType);
    }

    @Override
    public void delete(Long id) {
        ProductType type = productTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Type not found"));
        productTypeRepository.delete(type);

    }

    @Override
    public ProductTypeResponse getById(Long id) {
        ProductType type = productTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Type not found"));
        return map(type);
    }

    @Override
    public List<ProductTypeResponse> getAll() {
        return productTypeRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<ProductTypeResponse> getByCategory(Long categoryId) {
        return productTypeRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::map)
                .toList();
    }

    private ProductTypeResponse map(ProductType type) {
        ProductTypeResponse response = new ProductTypeResponse();
        response.setId(type.getId());
        response.setProductTypeName(type.getName());
        response.setActive(type.getActive());
        if (type.getCategory() != null) {
            response.setCategoryId(type.getCategory().getId());
            response.setCategoryName(type.getCategory().getName());
            if (type.getCategory().getCompany() != null) {
                response.setCompanyId(type.getCategory().getCompany().getId());
                response.setCompanyName(type.getCategory().getCompany().getName());
            }
        }
        return response;
    }
}
