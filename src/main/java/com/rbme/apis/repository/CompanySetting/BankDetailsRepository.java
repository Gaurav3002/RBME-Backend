package com.rbme.apis.repository.CompanySetting;

import com.rbme.apis.entity.CompanySettings.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
}
