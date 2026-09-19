package com.rbme.apis.controller;

import com.rbme.apis.dto.admin.category.ProductCategoryCreateRequest;
import com.rbme.apis.dto.admin.category.ProductCategoryResponse;
import com.rbme.apis.services.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    @PostMapping
    public ResponseEntity<ProductCategoryResponse> create(@Valid @RequestBody ProductCategoryCreateRequest request) {
        return new ResponseEntity<>(categoryService.create(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('CATEGORY_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> update(@PathVariable Long id, @Valid @RequestBody ProductCategoryCreateRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    @GetMapping
    public ResponseEntity<List<ProductCategoryResponse>> getAll() {

        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getById(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ProductCategoryResponse>> getByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(categoryService.getByCompany(companyId));
    }

    @PreAuthorize("hasAuthority('CATEGORY_EDIT')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam Boolean active) {
        categoryService.updateStatus(id, active);
        return ResponseEntity.ok("Category status updated successfully.");
    }

    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        categoryService.delete(id);

        return ResponseEntity.ok("Category deleted successfully.");
    }
}