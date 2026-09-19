package com.rbme.apis.repository.CompanySetting;

import com.rbme.apis.entity.CompanySettings.TaxInfoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxInfoDetailsRepository extends JpaRepository<TaxInfoDetails,Long> {
    boolean existsByTaxName(String taxName);
    boolean existsByTaxNameAndIdNot(String taxName, Long id);
}
