package com.rbme.apis.dto.CompanySettings;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaxInfoResponseDTO {

    private Long id;
    private String taxName;
    private int taxPercentage;
    private LocalDateTime createdOn;
    private Long createdBy;
}
