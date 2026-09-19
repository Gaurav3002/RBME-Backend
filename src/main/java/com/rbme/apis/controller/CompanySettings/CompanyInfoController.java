package com.rbme.apis.controller.CompanySettings;

import com.rbme.apis.dto.CompanySettings.CompanyInfoRequestDto;
import com.rbme.apis.dto.CompanySettings.CompanyInfoResponseDto;
import com.rbme.apis.services.CompanySettings.CompanyInfoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/companyinfo")
@RequiredArgsConstructor
public class CompanyInfoController {

    private final CompanyInfoImpl companyInfoService;


    @PreAuthorize(("hasAuthority('CREATE_COMPANYINFO')"))
    @PostMapping
    public ResponseEntity<CompanyInfoResponseDto> createCompany(@RequestBody CompanyInfoRequestDto dto) {
        CompanyInfoResponseDto response = companyInfoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize(("hasAuthority('VIEW_COMPANYINFO')"))
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyInfoResponseDto> getCompanyById(@PathVariable Long companyId) {
        CompanyInfoResponseDto response = companyInfoService.getById(companyId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize(("hasAuthority('VIEW_COMPANYINFO')"))
    @GetMapping
    public ResponseEntity<List<CompanyInfoResponseDto>> getAllCompanies() {
        List<CompanyInfoResponseDto> response = companyInfoService.getAll();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize(("hasAuthority('UPDATE_COMPANYINFO')"))
    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyInfoResponseDto> updateCompany(@PathVariable Long companyId, @RequestBody CompanyInfoRequestDto dto) {
        CompanyInfoResponseDto response = companyInfoService.update(companyId, dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize(("hasAuthority('DELETE_COMPANYINFO')"))
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long companyId) {
        companyInfoService.delete(companyId);
        return ResponseEntity.noContent().build();
    }
}