package com.rbme.apis.controller;

import com.rbme.apis.dto.admin.Company.CompanyCreateRequest;
import com.rbme.apis.dto.admin.Company.CompanyResponse;
import com.rbme.apis.dto.admin.Company.CompanyUpdateRequest;
import com.rbme.apis.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Create Company
     */
    @PreAuthorize("hasAuthority('COMPANY_CREATE')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CompanyResponse> createCompany(
            @ModelAttribute @Valid CompanyCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(request));
    }

    /**
     * Update Company
     */
    @PreAuthorize("hasAuthority('COMPANY_EDIT')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @ModelAttribute CompanyUpdateRequest request) {

        return ResponseEntity.ok(companyService.updateCompany(id, request));
    }

    /**
     * Get Company By Id
     */
    @PreAuthorize("hasAuthority('COMPANY_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    /**
     * Get All Companies
     */
    @PreAuthorize("hasAuthority('COMPANY_VIEW')")
    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {

        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    /**
     * Delete Company
     */
    @PreAuthorize("hasAuthority('COMPANY_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompany(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Update Company Status
     */
    @PreAuthorize("hasAuthority('COMPANY_EDIT')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {

        companyService.updateStatus(id, active);

        return ResponseEntity.ok().build();
    }

}