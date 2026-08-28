package com.rbme.apis.controller;

import com.rbme.apis.dto.admin.ProductType.ProductTypeRequest;
import com.rbme.apis.dto.admin.ProductType.ProductTypeResponse;
import com.rbme.apis.services.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product-types")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @PostMapping
    public ResponseEntity<ProductTypeResponse> create(@RequestBody ProductTypeRequest request) {
        return ResponseEntity.ok(productTypeService.createProductType(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductTypeResponse> update(
            @PathVariable Long id,
            @RequestBody ProductTypeRequest request) {

        return ResponseEntity.ok(productTypeService.updateProductType(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductTypeResponse>> getAll() {
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productTypeService.getById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductTypeResponse>> getByCategory(@PathVariable Long categoryId) {

        return ResponseEntity.ok(productTypeService.getByCategory(categoryId));
    }

}
