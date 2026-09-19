package com.rbme.apis.dto.CompanySettings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyInfoResponseDto {
    private Long companyId;
    private String companyName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state ;
    private String country ;
    private String zIPCode;
    private String phone;
    private String mobile;
    private String email;
    private String anotherEmail;
    private String gSTRegistered;
    private String gSTIN;
    private String pan;
    private String taxRegistrationType;
}
