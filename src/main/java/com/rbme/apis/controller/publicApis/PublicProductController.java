package com.rbme.apis.controller.publicApis;

import com.rbme.apis.dto.admin.Product.ProductResponse;
import com.rbme.apis.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class PublicProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productService.getById(id)
        );
    }




    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCompany(
            @PathVariable Long companyId
    ) {

        return ResponseEntity.ok(
                productService.getByCompany(companyId)
        );
    }



    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable Long categoryId
    ) {

        return ResponseEntity.ok(
                productService.getByCategory(categoryId)
        );
    }



    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<ProductResponse>> getProductsByProductType(
            @PathVariable Long typeId
    ) {

        return ResponseEntity.ok(
                productService.getByProductType(typeId)
        );
    }


    @GetMapping
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