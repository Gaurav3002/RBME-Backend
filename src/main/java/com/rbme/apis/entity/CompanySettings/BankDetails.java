package com.rbme.apis.entity.CompanySettings;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bank_details")
@Getter
@Setter
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankName;

    private String branchName;

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.INR;

    private Boolean primaryAccount = false;

    @Enumerated(EnumType.STRING)
    private BankStatus status = BankStatus.ACTIVE;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum AccountType {
        CURRENT,
        SAVINGS
    }

    public enum Currency {
        INR}

    public enum BankStatus {
        ACTIVE,
        INACTIVE
    }
}
