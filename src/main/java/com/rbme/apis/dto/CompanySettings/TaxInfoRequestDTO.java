package com.rbme.apis.dto.CompanySettings;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class TaxInfoRequestDTO {
    private String taxName;
    private int taxPercentage;
}
