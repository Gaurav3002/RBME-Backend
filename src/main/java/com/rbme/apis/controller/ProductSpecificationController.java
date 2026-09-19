package com.rbme.apis.controller;

import com.rbme.apis.dto.admin.ProductSpecification.ProductSpecificationRequest;
import com.rbme.apis.dto.admin.ProductSpecification.ProductSpecificationResponse;
import com.rbme.apis.services.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product-specifications")
@RequiredArgsConstructor
public class ProductSpecificationController {

    private final ProductSpecificationService
            productSpecificationService;

    @PreAuthorize("hasAuthority('PRODUCT_SPECIFICATION_CREATE')")
    @PostMapping
    public ResponseEntity<ProductSpecificationResponse> create(
            @RequestBody ProductSpecificationRequest request) {

        return ResponseEntity.ok(
                productSpecificationService.createSpecification(request)
        );
    }

    @PreAuthorize("hasAuthority('PRODUCT_SPECIFICATION_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductSpecificationResponse> update(
            @PathVariable Long id,
            @RequestBody ProductSpecificationRequest request) {

        return ResponseEntity.ok(
                productSpecificationService.updateSpecification(
                        id,
                        request
                )
        );
    }

    @PreAuthorize("hasAuthority('PRODUCT_SPECIFICATION_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productSpecificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('PRODUCT_SPECIFICATION_VIEW')")
    @GetMapping
    public ResponseEntity<List<ProductSpecificationResponse>> getAll() {
        return ResponseEntity.ok(productSpecificationService.getAll());
    }

    @PreAuthorize("hasAuthority('PRODUCT_SPECIFICATION_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductSpecificationResponse> getById(@PathVariable Long id) {

        return ResponseEntity.ok(productSpecificationService.getById(id));
    }

    @PreAuthorize("hasAuthority('PRODUCT_SPECIFICATION_VIEW')")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductSpecificationResponse>> getByProduct(@PathVariable Long productId) {

        return ResponseEntity.ok(productSpecificationService.getByProductId(productId));
    }
}