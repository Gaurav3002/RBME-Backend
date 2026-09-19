package com.rbme.apis.controller;

import com.rbme.apis.dto.admin.ProductType.ProductTypeRequest;
import com.rbme.apis.dto.admin.ProductType.ProductTypeResponse;
import com.rbme.apis.services.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product-types")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @PreAuthorize("hasAuthority('PRODUCT_TYPE_CREATE')")
    @PostMapping
    public ResponseEntity<ProductTypeResponse> create(@RequestBody ProductTypeRequest request) {
        return ResponseEntity.ok(productTypeService.createProductType(request));
    }

    @PreAuthorize("hasAuthority('PRODUCT_TYPE_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductTypeResponse> update(
            @PathVariable Long id,
            @RequestBody ProductTypeRequest request) {

        return ResponseEntity.ok(productTypeService.updateProductType(id, request));
    }

    @PreAuthorize("hasAuthority('PRODUCT_TYPE_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('PRODUCT_TYPE_VIEW')")
    @GetMapping
    public ResponseEntity<List<ProductTypeResponse>> getAll() {
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @PreAuthorize("hasAuthority('PRODUCT_TYPE_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productTypeService.getById(id));
    }

    @PreAuthorize("hasAuthority('PRODUCT_TYPE_VIEW')")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductTypeResponse>> getByCategory(@PathVariable Long categoryId) {

        return ResponseEntity.ok(productTypeService.getByCategory(categoryId));
    }

}
