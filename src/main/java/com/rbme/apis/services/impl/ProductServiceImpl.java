package com.rbme.apis.services.impl;

import com.rbme.apis.dto.admin.Product.ProductImageResponse;
import com.rbme.apis.dto.admin.Product.ProductRequest;
import com.rbme.apis.dto.admin.Product.ProductResponse;
import com.rbme.apis.dto.admin.ProductSpecification.ProductSpecificationResponse;
import com.rbme.apis.entity.*;
import com.rbme.apis.exception.DuplicateResourceException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.*;
import com.rbme.apis.services.FileStorageService;
import com.rbme.apis.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ImageRepository productImageRepository;
    private final FileStorageService fileStorageService;
    private final SpecificationRepository productSpecificationRepository;


    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found!"));
        ProductCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found!"));
        ProductType type = productTypeRepository.findById(request.getProductTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product Type not found!"));
        if(!category.getCompany().getId().equals(company.getId())){
            throw new IllegalArgumentException("Category not belong to that company");
        }
        if(!type.getCategory().getId().equals(category.getId())){
            throw new IllegalArgumentException("Product Type not belongs to that Category");
        }
        if (request.getModelNo() != null
                && !request.getModelNo().isBlank()
                && productRepository.existsByModelNoIgnoreCase(
                request.getModelNo()
        )) {

            throw new DuplicateResourceException(
                    "Product with model number already exists!"
            );
        }
        Product product = new Product();

        product.setCompany(company);
        product.setCategory(category);
        product.setProductType(type);
        product.setModelNo(request.getModelNo());
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setThumbnail(request.getThumbnail());
        product.setYoutubeUrl(request.getYoutubeUrl());
        product.setFeatured(
                request.getFeatured() != null && request.getFeatured()
        );
        product.setActive(
                request.getActive() == null || request.getActive()
        );
        Product savedProduct = productRepository.save(product);
        saveProductImages(savedProduct, request.getImages()
        );
        return map(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found!"));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found!"));

        ProductCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found!"));

        ProductType type = productTypeRepository.findById(request.getProductTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product Type not found!"));

        if (!category.getCompany().getId().equals(company.getId())) {
            throw new IllegalArgumentException(
                    "Category does not belong to selected company!"
            );
        }

        if (!type.getCategory().getId().equals(category.getId())) {
            throw new IllegalArgumentException(
                    "Product Type does not belong to selected category!"
            );
        }

        if (request.getModelNo() != null
                && !request.getModelNo().isBlank()
                && productRepository.existsByModelNoIgnoreCaseAndIdNot(
                request.getModelNo(),
                id
        )) {

            throw new DuplicateResourceException(
                    "Product with this model number already exists!"
            );
        }

        // ==========================================
        // UPDATE PRODUCT
        // ==========================================

        product.setCompany(company);
        product.setCategory(category);
        product.setProductType(type);
        product.setModelNo(request.getModelNo());
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setThumbnail(request.getThumbnail());
        product.setYoutubeUrl(request.getYoutubeUrl());

        if (request.getFeatured() != null) {
            product.setFeatured(request.getFeatured());
        }

        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }

        Product updatedProduct = productRepository.save(product);

        // ==========================================
        // DELETE SELECTED IMAGES
        // ==========================================

        System.out.println("Product ID: " + updatedProduct.getId());
        System.out.println("Deleted Image IDs: " + request.getDeletedImageIds());

        if (request.getDeletedImageIds() != null
                && !request.getDeletedImageIds().isEmpty()) {

            for (Long imageId : request.getDeletedImageIds()) {

                System.out.println(
                        "Trying to delete imageId: " + imageId
                                + " for productId: " + updatedProduct.getId()
                );

                int deleted = productImageRepository.deleteByIdAndProductId(
                        imageId,
                        updatedProduct.getId()
                );

                System.out.println(
                        "Delete result for imageId "
                                + imageId + ": " + deleted
                );

                if (deleted == 0) {
                    throw new ResourceNotFoundException(
                            "Product image not found or does not belong to this product: "
                                    + imageId
                    );
                }
            }
        }

        // ==========================================
        // ADD NEW IMAGES
        // ==========================================

        if (request.getImages() != null
                && !request.getImages().isEmpty()) {

            saveProductImages(
                    updatedProduct,
                    request.getImages()
            );
        }

        return map(updatedProduct);
    }

    private void saveProductImages(Product product, List<MultipartFile> images) {

        if (images == null || images.isEmpty()) {
            return;
        }


        int sortOrder = 0;


        for (MultipartFile file : images) {

            if (file == null || file.isEmpty()) {
                continue;
            }


            // =============================================
            // UPLOAD FILE
            // =============================================

            String imagePath = fileStorageService.uploadFile(file, "product/images");
            if (imagePath == null) {
                continue;
            }

            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setImageUrl(imagePath);
            productImage.setSortOrder(sortOrder);
            productImageRepository.save(productImage);
            sortOrder++;
        }
    }


    private void deleteProductImages(Product product) {

        List<ProductImage> images = productImageRepository.findByProductIdOrderBySortOrderAsc(product.getId());
        for (ProductImage image : images) {
            fileStorageService.deleteFile(image.getImageUrl());
        }
        productImageRepository.deleteByProductId(product.getId());
    }



    @Override
    @Transactional
    public void delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        productSpecificationRepository.deleteByProductId(product.getId());
        deleteProductImages(product);
        productRepository.delete(product);
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found!"));

        return map(product);
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<ProductResponse> getByCompany(Long companyId) {
        return productRepository.findByCompanyId(companyId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<ProductResponse> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<ProductResponse> getByProductType(Long typeId) {
        return productRepository.findByProductTypeId(typeId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<ProductResponse> getActiveProducts() {
        return productRepository.findByActive(true)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<ProductResponse> getFeaturedProducts() {
        return productRepository.findByFeatured(true)
                .stream()
                .map(this::map)
                .toList();
    }

    private ProductResponse map(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());

        response.setModelNo(product.getModelNo());
        response.setTitle(product.getTitle());
        response.setDescription(product.getDescription());
        response.setThumbnail(product.getThumbnail());
        response.setYoutubeUrl(product.getYoutubeUrl());
        response.setFeatured(product.isFeatured());
        response.setActive(product.isActive());

        if (product.getCompany() != null) {

            response.setCompanyId(
                    product.getCompany().getId()
            );

            response.setCompanyName(
                    product.getCompany().getName()
            );

            response.setCompanyBanner(
                    product.getCompany().getBanner()
            );
        }

        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }

        if (product.getProductType() != null) {
            response.setProductTypeId(product.getProductType().getId());
            response.setProductTypeName(product.getProductType().getName());
        }
        List<ProductImageResponse> imageResponses = new ArrayList<>();
        if (product.getImages() != null) {
            for (ProductImage image : product.getImages()) {
                ProductImageResponse imageResponse = new ProductImageResponse();
                imageResponse.setId(image.getId());
                imageResponse.setImageUrl(image.getImageUrl());
                imageResponse.setSortOrder(image.getSortOrder());
                imageResponses.add(imageResponse);
            }
        }
        response.setImages(imageResponses);

        List<ProductSpecificationResponse> specificationResponses = new ArrayList<>();
        List<ProductSpecification> specifications = productSpecificationRepository.findByProductId(product.getId());
        for (ProductSpecification specification : specifications) {
            ProductSpecificationResponse specificationResponse = new ProductSpecificationResponse();
            specificationResponse.setSpecificationName(specification.getSpecificationName());
            specificationResponse.setSpecificationValue(specification.getSpecificationValue());
            specificationResponses.add(specificationResponse);
        }
        response.setSpecifications(specificationResponses);

        return response;
    }

}
