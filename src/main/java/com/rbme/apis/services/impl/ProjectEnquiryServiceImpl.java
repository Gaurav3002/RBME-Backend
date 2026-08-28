package com.rbme.apis.services.impl;

import com.rbme.apis.dto.admin.ProjectEnquiryRequest;
import com.rbme.apis.dto.admin.ProjectEnquiryResponse;
import com.rbme.apis.entity.Company;
import com.rbme.apis.entity.ProjectEnquiry;
import com.rbme.apis.repository.CompanyRepository;
import com.rbme.apis.repository.ProjectEnquiryRepository;
import com.rbme.apis.services.ProjectEnquiryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectEnquiryServiceImpl
        implements ProjectEnquiryService {


    private final ProjectEnquiryRepository enquiryRepository;

    private final CompanyRepository companyRepository;


    public ProjectEnquiryServiceImpl(ProjectEnquiryRepository enquiryRepository, CompanyRepository companyRepository) {
        this.enquiryRepository = enquiryRepository;
        this.companyRepository = companyRepository;
    }



    @Override
    public ProjectEnquiryResponse createEnquiry(
            ProjectEnquiryRequest request
    ) {

        Company company =
                companyRepository.findById(request.getCompanyId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found with id: "
                                                + request.getCompanyId()
                                )
                        );


        ProjectEnquiry enquiry =
                new ProjectEnquiry();


        enquiry.setCompany(company);

        enquiry.setCategory(request.getCategory());

        enquiry.setMachine(request.getMachine());

        enquiry.setProjectType(
                request.getProjectType()
        );

        enquiry.setCapacity(
                request.getCapacity()
        );

        enquiry.setProduct(
                request.getProduct()
        );

        enquiry.setAutomation(
                request.getAutomation()
        );

        enquiry.setBudget(
                request.getBudget()
        );

        enquiry.setMessage(
                request.getMessage()
        );


        enquiry.setName(
                request.getName()
        );

        enquiry.setBusinessName(
                request.getBusinessName()
        );

        enquiry.setEmail(
                request.getEmail()
        );

        enquiry.setPhone(
                request.getPhone()
        );

        enquiry.setCity(
                request.getCity()
        );

        enquiry.setState(
                request.getState()
        );


        /*
         * New enquiries always start as NEW.
         */

        enquiry.setStatus("NEW");


        ProjectEnquiry saved =
                enquiryRepository.save(enquiry);


        return mapToResponse(saved);
    }


    /* =========================================================
       GET ALL
    ========================================================= */

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEnquiryResponse> getAllEnquiries() {

        return enquiryRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    /* =========================================================
       GET BY ID
    ========================================================= */

    @Override
    @Transactional(readOnly = true)
    public ProjectEnquiryResponse getEnquiryById(Long id) {

        ProjectEnquiry enquiry =
                enquiryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Enquiry not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(enquiry);
    }


    /* =========================================================
       GET BY COMPANY
    ========================================================= */

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEnquiryResponse> getEnquiriesByCompany(
            Long companyId
    ) {

        return enquiryRepository
                .findByCompanyIdOrderByCreatedAtDesc(companyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    /* =========================================================
       GET BY STATUS
    ========================================================= */

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEnquiryResponse> getEnquiriesByStatus(
            String status
    ) {

        return enquiryRepository
                .findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    /* =========================================================
       UPDATE STATUS
    ========================================================= */

    @Override
    public ProjectEnquiryResponse updateStatus(
            Long id,
            String status
    ) {

        ProjectEnquiry enquiry =
                enquiryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Enquiry not found with id: "
                                                + id
                                )
                        );


        enquiry.setStatus(
                status.toUpperCase()
        );


        ProjectEnquiry updated =
                enquiryRepository.save(enquiry);


        return mapToResponse(updated);
    }


    /* =========================================================
       MAPPER
    ========================================================= */

    private ProjectEnquiryResponse mapToResponse(
            ProjectEnquiry enquiry
    ) {

        ProjectEnquiryResponse response =
                new ProjectEnquiryResponse();


        response.setId(
                enquiry.getId()
        );


        if (enquiry.getCompany() != null) {

            response.setCompanyId(
                    enquiry.getCompany().getId()
            );

            response.setCompanyName(
                    enquiry.getCompany().getName()
            );
        }


        response.setCategory(
                enquiry.getCategory()
        );

        response.setMachine(
                enquiry.getMachine()
        );

        response.setProjectType(
                enquiry.getProjectType()
        );

        response.setCapacity(
                enquiry.getCapacity()
        );

        response.setProduct(
                enquiry.getProduct()
        );

        response.setAutomation(
                enquiry.getAutomation()
        );

        response.setBudget(
                enquiry.getBudget()
        );

        response.setMessage(
                enquiry.getMessage()
        );


        response.setName(
                enquiry.getName()
        );

        response.setBusinessName(
                enquiry.getBusinessName()
        );

        response.setEmail(
                enquiry.getEmail()
        );

        response.setPhone(
                enquiry.getPhone()
        );

        response.setCity(
                enquiry.getCity()
        );

        response.setState(
                enquiry.getState()
        );


        response.setStatus(
                enquiry.getStatus()
        );

        response.setCreatedAt(
                enquiry.getCreatedAt()
        );


        return response;
    }
}