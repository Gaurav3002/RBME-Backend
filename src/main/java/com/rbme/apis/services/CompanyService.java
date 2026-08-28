package com.rbme.apis.services;

import com.rbme.apis.dto.admin.Company.CompanyCreateRequest;
import com.rbme.apis.dto.admin.Company.CompanyResponse;
import com.rbme.apis.dto.admin.Company.CompanyUpdateRequest;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(CompanyCreateRequest request);

    CompanyResponse updateCompany(Long id, CompanyUpdateRequest request);

    CompanyResponse getCompanyById(Long id);

    List<CompanyResponse> getAllCompanies();

    void deleteCompany(Long id);

    void updateStatus(Long id, Boolean active);
    List<CompanyResponse> getActiveCompanies();

    CompanyResponse getActiveCompanyById(Long id);

}