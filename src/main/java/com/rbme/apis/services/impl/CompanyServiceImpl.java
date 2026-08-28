package com.rbme.apis.services.impl;

import com.rbme.apis.dto.admin.Company.CompanyCreateRequest;
import com.rbme.apis.dto.admin.Company.CompanyResponse;
import com.rbme.apis.dto.admin.Company.CompanyUpdateRequest;
import com.rbme.apis.entity.Company;
import com.rbme.apis.exception.DuplicateResourceException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.CompanyRepository;
import com.rbme.apis.services.CompanyService;
import com.rbme.apis.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final FileStorageService fileStorageService;

    @Override
    public CompanyResponse createCompany(CompanyCreateRequest request) {

        if (companyRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Company name already exists.");
        }

        Company company = new Company();

        company.setName(request.getName());
        company.setSlug(generateSlug(request.getName()));

        company.setLogo(
                fileStorageService.uploadFile(
                        request.getLogo(),
                        "company/logo"
                )
        );

        company.setBanner(
                fileStorageService.uploadFile(
                        request.getBanner(),
                        "company/banner"
                )
        );

        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setActive(Boolean.TRUE.equals(request.getActive()));

        Company savedCompany = companyRepository.save(company);

        return mapToResponse(savedCompany);
    }

    @Override
    public CompanyResponse updateCompany(Long id, CompanyUpdateRequest request) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        if (request.getName() != null &&
                !request.getName().isBlank() &&
                !request.getName().equalsIgnoreCase(company.getName())) {

            if (companyRepository.existsByName(request.getName())) {
                throw new DuplicateResourceException("Company name already exists.");
            }

            company.setName(request.getName());
            company.setSlug(generateSlug(request.getName()));
        }

        if (request.getLogo() != null && !request.getLogo().isEmpty()) {

            fileStorageService.deleteFile(company.getLogo());

            company.setLogo(
                    fileStorageService.uploadFile(
                            request.getLogo(),
                            "company/logo"
                    )
            );
        }

        if (request.getBanner() != null && !request.getBanner().isEmpty()) {

            fileStorageService.deleteFile(company.getBanner());

            company.setBanner(
                    fileStorageService.uploadFile(
                            request.getBanner(),
                            "company/banner"
                    )
            );
        }

        if (request.getDescription() != null) {
            company.setDescription(request.getDescription());
        }

        if (request.getWebsite() != null) {
            company.setWebsite(request.getWebsite());
        }

        if (request.getActive() != null) {
            company.setActive(request.getActive());
        }

        Company updatedCompany = companyRepository.save(company);

        return mapToResponse(updatedCompany);
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        return mapToResponse(company);
    }

    @Override
    public List<CompanyResponse> getAllCompanies() {

        return companyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteCompany(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        fileStorageService.deleteFile(company.getLogo());
        fileStorageService.deleteFile(company.getBanner());

        companyRepository.delete(company);
    }

    @Override
    public void updateStatus(Long id, Boolean active) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found."));

        company.setActive(active);

        companyRepository.save(company);
    }

    @Override
    public List<CompanyResponse> getActiveCompanies() {
        return companyRepository.findByActiveTrue().stream().map(this::mapToResponse).toList();
    }

    @Override
    public CompanyResponse getActiveCompanyById(Long id) {
        Company company = companyRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not exist"));
        return mapToResponse(company);
    }

    private CompanyResponse mapToResponse(Company company) {

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .logo(company.getLogo())
                .banner(company.getBanner())
                .description(company.getDescription())
                .website(company.getWebsite())
                .active(company.getActive())
                .build();
    }

    private String generateSlug(String name) {

        return name.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }

}