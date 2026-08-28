package com.rbme.apis.repository;

import com.rbme.apis.entity.ProjectEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectEnquiryRepository extends JpaRepository<ProjectEnquiry, Long> {

    List<ProjectEnquiry> findAllByOrderByCreatedAtDesc();

    List<ProjectEnquiry> findByCompanyIdOrderByCreatedAtDesc(Long companyId);

    List<ProjectEnquiry> findByStatusOrderByCreatedAtDesc(String status);
}
