package com.rbme.apis.entity.CompanySettings;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="companyInfo")
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
