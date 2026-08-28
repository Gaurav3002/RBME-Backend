package com.rbme.apis.services.impl;

import com.rbme.apis.dto.admin.ProductSpecification.ProductSpecificationRequest;
import com.rbme.apis.dto.admin.ProductSpecification.ProductSpecificationResponse;
import com.rbme.apis.entity.Product;
import com.rbme.apis.entity.ProductSpecification;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.ProductRepository;
import com.rbme.apis.repository.SpecificationRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSpecificationService implements com.rbme.apis.services.ProductSpecificationService {
    private final SpecificationRepository repository;
    private final ProductRepository productRepository;

    @Override
    public ProductSpecificationResponse createSpecification(ProductSpecificationRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Specification Not Available for this Product"));
        ProductSpecification specification = new ProductSpecification();
        specification.setProduct(product);
        specification.setSpecificationName(
                request.getSpecificationName()
        );
        specification.setSpecificationValue(
                request.getSpecificationValue()
        );

        ProductSpecification saved =
                repository.save(specification);
        return map(saved);
    }

    @Override
    public ProductSpecificationResponse updateSpecification(Long id, ProductSpecificationRequest request) {
        ProductSpecification specification =
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product Specification not found!"));

        Product product = productRepository.findById(request.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        specification.setProduct(product);

        specification.setSpecificationName(request.getSpecificationName());
        specification.setSpecificationValue(request.getSpecificationValue());

        ProductSpecification updated = repository.save(specification);
        return map(updated);
    }

    @Override
    public ProductSpecificationResponse getById(Long id) {
      ProductSpecification specification =  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Specification Not available!"));
      return map(specification);
    }

    @Override
    public void delete(Long id) {
        ProductSpecification specification = repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Product Specification not found!"));
        repository.delete(specification);
    }

    @Override
    public List<ProductSpecificationResponse> getAll() {
        return repository.findAll().stream().map(this::map).toList();

    }

    @Override
    public List<ProductSpecificationResponse> getByProductId(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found!"));
        return repository.findByProductId(productId)
                .stream()
                .map(this::map)
                .toList();
    }

    private ProductSpecificationResponse map(
            ProductSpecification specification) {

        ProductSpecificationResponse response =
                new ProductSpecificationResponse();

        response.setId(specification.getId());

        Product product = specification.getProduct();

        if (product != null) {
            response.setProductId(
                    product.getId()
            );

            response.setProductName(
                    product.getModelNo()
                            + " ("
                            + product.getTitle()
                            + ")"
            );



            if (product.getCompany() != null) {

                response.setCompanyId(
                        product.getCompany().getId()
                );

                response.setCompanyName(
                        product.getCompany().getName()
                );
            }


            if (product.getCategory() != null) {

                response.setCategoryId(
                        product.getCategory().getId()
                );

                response.setCategoryName(
                        product.getCategory().getName()
                );
            }


            if (product.getProductType() != null) {

                response.setProductTypeId(
                        product.getProductType().getId()
                );

                response.setProductTypeName(
                        product.getProductType()
                                .getName()
                );
            }
        }




        response.setSpecificationName(
                specification.getSpecificationName()
        );

        response.setSpecificationValue(
                specification.getSpecificationValue()
        );


        return response;
    }
}
