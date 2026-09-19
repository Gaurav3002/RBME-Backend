package com.rbme.apis.repository.CompanySetting;

import com.rbme.apis.entity.CompanySettings.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long> {

    boolean existsByCompanyName(String companyName);
    boolean existsByCompanyNameAndCompanyIdNot(String companyName, Long companyId);
    Optional<CompanyInfo> findByCompanyId(Long companyId);
}