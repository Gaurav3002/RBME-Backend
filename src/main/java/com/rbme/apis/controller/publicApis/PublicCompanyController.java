package com.rbme.apis.controller.publicApis;

import com.rbme.apis.dto.admin.Company.CompanyResponse;
import com.rbme.apis.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class PublicCompanyController {
    public final CompanyService companyService;


    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getActiveCompanies() {
        return ResponseEntity.ok(companyService.getActiveCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getActiveCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getActiveCompanyById(id));
    }
}
