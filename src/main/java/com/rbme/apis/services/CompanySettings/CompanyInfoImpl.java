package com.rbme.apis.services.CompanySettings;

import com.rbme.apis.dto.CompanySettings.CompanyInfoRequestDto;
import com.rbme.apis.dto.CompanySettings.CompanyInfoResponseDto;
import com.rbme.apis.entity.CompanySettings.CompanyInfo;
import com.rbme.apis.exception.BadRequestException;
import com.rbme.apis.repository.CompanySetting.CompanyInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyInfoImpl {

    private final CompanyInfoRepository companyInfoRepository;


    public CompanyInfoResponseDto create(CompanyInfoRequestDto dto) {

        if (dto == null) {
            throw new BadRequestException("Company information is required!");
        }

        if (dto.getCompanyName() == null || dto.getCompanyName().trim().isEmpty()) {
            throw new BadRequestException("Company Name is required!");
        }

        if (companyInfoRepository.existsByCompanyName(dto.getCompanyName().trim())) {
            throw new BadRequestException("Company Already Exist!");
        }

        CompanyInfo companyInfo = new CompanyInfo();
        mapDtoToEntity(dto, companyInfo);
        CompanyInfo savedCompany = companyInfoRepository.save(companyInfo);
        return convertToDto(savedCompany);
    }


    public CompanyInfoResponseDto getById(Long companyId) {
        if (companyId == null) {
            throw new BadRequestException("Company ID is required!");
        }

        CompanyInfo companyInfo = companyInfoRepository.findByCompanyId(companyId)
                        .orElseThrow(() -> new BadRequestException("Company information not found!"));
        return convertToDto(companyInfo);
    }


    public List<CompanyInfoResponseDto> getAll() {

        return companyInfoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public CompanyInfoResponseDto update(Long companyId, CompanyInfoRequestDto dto) {

        if (companyId == null) {
            throw new BadRequestException("Company ID is required!");
        }

        if (dto == null) {
            throw new BadRequestException("Company information is required!");
        }

        if (dto.getCompanyName() == null || dto.getCompanyName().trim().isEmpty()) {
            throw new BadRequestException("Company Name is required!");
        }

        CompanyInfo companyInfo = companyInfoRepository.findByCompanyId(companyId)
                        .orElseThrow(() -> new BadRequestException("Company information not found!"));

        boolean companyNameExists = companyInfoRepository.existsByCompanyNameAndCompanyIdNot(dto.getCompanyName().trim(), companyId);

        if (companyNameExists) {
            throw new BadRequestException("Company Already Exist!");
        }

        mapDtoToEntity(dto, companyInfo);

        CompanyInfo updatedCompany = companyInfoRepository.save(companyInfo);
        return convertToDto(updatedCompany);
    }


    public void delete(Long companyId) {

        if (companyId == null) {
            throw new BadRequestException("Company ID is required!");
        }

        CompanyInfo companyInfo = companyInfoRepository.findByCompanyId(companyId)
                        .orElseThrow(() ->
                                new BadRequestException("Company information not found!"));
        companyInfoRepository.delete(companyInfo);
    }


    private void mapDtoToEntity(CompanyInfoRequestDto dto, CompanyInfo companyInfo) {

        companyInfo.setCompanyName(dto.getCompanyName() != null ? dto.getCompanyName().trim() : null);
        companyInfo.setAddressLine1(dto.getAddressLine1());
        companyInfo.setAddressLine2(dto.getAddressLine2());
        companyInfo.setCity(dto.getCity());
        companyInfo.setState(dto.getState());
        companyInfo.setCountry(dto.getCountry());
        companyInfo.setZIPCode(dto.getZIPCode());
        companyInfo.setPhone(dto.getPhone());
        companyInfo.setMobile(dto.getMobile());
        companyInfo.setEmail(dto.getEmail());
        companyInfo.setAnotherEmail(dto.getAnotherEmail());
        companyInfo.setGSTRegistered(dto.getGSTRegistered());
        companyInfo.setGSTIN(dto.getGSTIN());
        companyInfo.setPan(dto.getPan());
        companyInfo.setTaxRegistrationType(dto.getTaxRegistrationType());
    }



    private CompanyInfoResponseDto convertToDto(CompanyInfo companyInfo) {

        CompanyInfoResponseDto dto = new CompanyInfoResponseDto();
        dto.setCompanyId(companyInfo.getCompanyId());
        dto.setCompanyName(companyInfo.getCompanyName());
        dto.setAddressLine1(companyInfo.getAddressLine1());
        dto.setAddressLine2(companyInfo.getAddressLine2());
        dto.setCity(companyInfo.getCity());
        dto.setState(companyInfo.getState());
        dto.setCountry(companyInfo.getCountry());
        dto.setZIPCode(companyInfo.getZIPCode());
        dto.setPhone(companyInfo.getPhone());
        dto.setMobile(companyInfo.getMobile());
        dto.setEmail(companyInfo.getEmail());
        dto.setAnotherEmail(companyInfo.getAnotherEmail());
        dto.setGSTRegistered(companyInfo.getGSTRegistered());
        dto.setGSTIN(companyInfo.getGSTIN());
        dto.setPan(companyInfo.getPan());
        dto.setTaxRegistrationType(companyInfo.getTaxRegistrationType());
        return dto;
    }
}