package com.rbme.apis.services;

import com.rbme.apis.dto.admin.ProjectEnquiryRequest;
import com.rbme.apis.dto.admin.ProjectEnquiryResponse;

import java.util.List;

public interface ProjectEnquiryService {

    ProjectEnquiryResponse createEnquiry(ProjectEnquiryRequest request);

    List<ProjectEnquiryResponse> getAllEnquiries();

    ProjectEnquiryResponse getEnquiryById(Long id);

    List<ProjectEnquiryResponse> getEnquiriesByCompany(Long companyId);

    List<ProjectEnquiryResponse> getEnquiriesByStatus(String status);

    ProjectEnquiryResponse updateStatus(Long id, String status);
}
