package com.rbme.apis.controller;

import com.rbme.apis.dto.admin.Product.ProductRequest;
import com.rbme.apis.dto.admin.Product.ProductResponse;
import com.rbme.apis.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;



    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @ModelAttribute ProductRequest request) {

        return ResponseEntity.ok(
                productService.createProduct(request)
        );
    }


    @PreAuthorize("hasAuthority('PRODUCT_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @ModelAttribute ProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }


    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        productService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {

        return ResponseEntity.ok(
                productService.getAll()
        );
    }


    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getById(id)
        );
    }


    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ProductResponse>> getByCompany(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                productService.getByCompany(companyId)
        );
    }

    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                productService.getByCategory(categoryId)
        );
    }


    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/productType/{productTypeId}")
    public ResponseEntity<List<ProductResponse>> getByType(
            @PathVariable Long productTypeId) {

        return ResponseEntity.ok(
                productService.getByProductType(productTypeId)
        );
    }

    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/active")
    public ResponseEntity<List<ProductResponse>> getActiveProducts() {

        return ResponseEntity.ok(
                productService.getActiveProducts()
        );
    }

    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    @GetMapping("/featured")
    public ResponseEntity<List<ProductResponse>> getFeaturedProducts() {

        return ResponseEntity.ok(
                productService.getFeaturedProducts()
        );
    }
}