package com.rbme.apis.services.impl;

import com.rbme.apis.dto.admin.category.ProductCategoryCreateRequest;
import com.rbme.apis.dto.admin.category.ProductCategoryResponse;
import com.rbme.apis.entity.Company;
import com.rbme.apis.entity.ProductCategory;
import com.rbme.apis.exception.DuplicateResourceException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.CompanyRepository;
import com.rbme.apis.repository.ProductCategoryRepository;
import com.rbme.apis.services.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;

    @Override
    public ProductCategoryResponse create(ProductCategoryCreateRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not Found"));
        if (categoryRepository.existsByNameAndCompanyId(
                request.getName(),
                company.getId())) {

            throw new DuplicateResourceException("Category already exists.");
        }

        ProductCategory category = new ProductCategory();

        category.setName(request.getName());
        category.setCompany(company);
        category.setActive(request.isActive());

        categoryRepository.save(category);

        return map(category);

    }

    @Override
    public ProductCategoryResponse update(Long id, ProductCategoryCreateRequest request) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByNameAndCompanyId(
                request.getName(),
                request.getCompanyId())) {

            throw new DuplicateResourceException("Category already exists.");
        }

        category.setName(request.getName());
        category.setCompany(company);
        category.setActive(request.isActive());

        categoryRepository.save(category);

        return map(category);

    }

    @Override
    public void delete(Long id) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        categoryRepository.delete(category);
    }

    @Override
    public ProductCategoryResponse getCategoryById(Long id) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        return map(category);
    }

    @Override
    public List<ProductCategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public List<ProductCategoryResponse> getByCompany(Long companyId) {
        return categoryRepository.findByCompanyId(companyId).stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long id, Boolean active) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        category.setActive(active);

        categoryRepository.save(category);
    }
    private ProductCategoryResponse map(ProductCategory category) {

        ProductCategoryResponse response = new ProductCategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setActive(category.getActive());
        response.setCompanyId(category.getCompany().getId());
        response.setCompanyName(category.getCompany().getName());

        return response;
    }
}
