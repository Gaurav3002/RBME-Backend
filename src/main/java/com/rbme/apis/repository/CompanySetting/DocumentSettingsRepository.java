package com.rbme.apis.repository.CompanySetting;

import com.rbme.apis.entity.CompanySettings.DocumentSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentSettingsRepository extends JpaRepository<DocumentSettings, Long> {

}
