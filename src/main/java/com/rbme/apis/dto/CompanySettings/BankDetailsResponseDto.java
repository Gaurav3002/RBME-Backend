package com.rbme.apis.dto.CompanySettings;

import com.rbme.apis.entity.CompanySettings.BankDetails;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BankDetailsResponseDto {

    private Long id;

    private String bankName;

    private String branchName;

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private BankDetails.AccountType accountType;

    private BankDetails.Currency currency;

    private Boolean primaryAccount;

    private BankDetails.BankStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

