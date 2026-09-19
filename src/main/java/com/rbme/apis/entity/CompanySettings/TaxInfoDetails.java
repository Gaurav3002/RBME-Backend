package com.rbme.apis.entity.CompanySettings;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tax_infoDetails")
public class TaxInfoDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "TaxName not Empty")
    private String taxName;

    @NotNull(message = "texPercentage not empty")
    @Min(value = 0, message = "Tax percentage cannot be less than 0")
    private int taxPercentage;
    private LocalDateTime CreatedOn;
    private Long  createdBy;
}
