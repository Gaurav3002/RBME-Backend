package com.rbme.apis.controller;

import com.rbme.apis.dto.admin.Product.ProductRequest;
import com.rbme.apis.dto.admin.Product.ProductResponse;
import com.rbme.apis.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;



    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @ModelAttribute ProductRequest request) {

        return ResponseEntity.ok(
                productService.createProduct(request)
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @ModelAttribute ProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        productService.delete(id);

        return ResponseEntity.noContent().build();
    }



    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {

        return ResponseEntity.ok(
                productService.getAll()
        );
    }



    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getById(id)
        );
    }



    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ProductResponse>> getByCompany(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                productService.getByCompany(companyId)
        );
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                productService.getByCategory(categoryId)
        );
    }



    @GetMapping("/productType/{productTypeId}")
    public ResponseEntity<List<ProductResponse>> getByType(
            @PathVariable Long productTypeId) {

        return ResponseEntity.ok(
                productService.getByProductType(productTypeId)
        );
    }


    @GetMapping("/active")
    public ResponseEntity<List<ProductResponse>> getActiveProducts() {

        return ResponseEntity.ok(
                productService.getActiveProducts()
        );
    }


    @GetMapping("/featured")
    public ResponseEntity<List<ProductResponse>> getFeaturedProducts() {

        return ResponseEntity.ok(
                productService.getFeaturedProducts()
        );
    }
}