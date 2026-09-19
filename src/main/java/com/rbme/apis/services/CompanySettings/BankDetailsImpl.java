package com.rbme.apis.services.CompanySettings;

import com.rbme.apis.dto.CompanySettings.BankDetailsRequestDto;
import com.rbme.apis.dto.CompanySettings.BankDetailsResponseDto;
import com.rbme.apis.entity.CompanySettings.BankDetails;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.CompanySetting.BankDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankDetailsImpl {

    private final BankDetailsRepository bankDetailsRepository;

    // CREATE
    public BankDetailsResponseDto createBankDetails(BankDetailsRequestDto dto) {

        BankDetails bankDetails = new BankDetails();

        bankDetails.setBankName(dto.getBankName());
        bankDetails.setBranchName(dto.getBranchName());
        bankDetails.setAccountHolderName(dto.getAccountHolderName());
        bankDetails.setAccountNumber(dto.getAccountNumber());
        bankDetails.setIfscCode(dto.getIfscCode());
        bankDetails.setAccountType(dto.getAccountType());
        bankDetails.setCurrency(dto.getCurrency());
        bankDetails.setPrimaryAccount(dto.getPrimaryAccount());
        bankDetails.setStatus(dto.getStatus());

        BankDetails saved = bankDetailsRepository.save(bankDetails);

        return convertToResponse(saved);
    }

    // GET BY ID
    public BankDetailsResponseDto getBankDetailsById(Long id) {

        BankDetails bankDetails = bankDetailsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bank details not found with id: " + id
                        )
                );

        return convertToResponse(bankDetails);
    }

    // GET ALL
    public List<BankDetailsResponseDto> getAllBankDetails() {

        return bankDetailsRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE
    public BankDetailsResponseDto updateBankDetails(
            Long id,
            BankDetailsRequestDto dto) {

        BankDetails bankDetails = bankDetailsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bank details not found with id: " + id
                        )
                );

        bankDetails.setBankName(dto.getBankName());
        bankDetails.setBranchName(dto.getBranchName());
        bankDetails.setAccountHolderName(dto.getAccountHolderName());
        bankDetails.setAccountNumber(dto.getAccountNumber());
        bankDetails.setIfscCode(dto.getIfscCode());
        bankDetails.setAccountType(dto.getAccountType());
        bankDetails.setCurrency(dto.getCurrency());
        bankDetails.setPrimaryAccount(dto.getPrimaryAccount());
        bankDetails.setStatus(dto.getStatus());

        BankDetails updated = bankDetailsRepository.save(bankDetails);

        return convertToResponse(updated);
    }

    // DELETE
    public void deleteBankDetails(Long id) {

        if (!bankDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Bank details not found with id: " + id
            );
        }

        bankDetailsRepository.deleteById(id);
    }

    // ENTITY -> RESPONSE DTO
    private BankDetailsResponseDto convertToResponse(
            BankDetails bankDetails) {

        BankDetailsResponseDto dto = new BankDetailsResponseDto();

        dto.setId(bankDetails.getId());
        dto.setBankName(bankDetails.getBankName());
        dto.setBranchName(bankDetails.getBranchName());
        dto.setAccountHolderName(bankDetails.getAccountHolderName());
        dto.setAccountNumber(bankDetails.getAccountNumber());
        dto.setIfscCode(bankDetails.getIfscCode());
        dto.setAccountType(bankDetails.getAccountType());
        dto.setCurrency(bankDetails.getCurrency());
        dto.setPrimaryAccount(bankDetails.getPrimaryAccount());
        dto.setStatus(bankDetails.getStatus());
        dto.setCreatedAt(bankDetails.getCreatedAt());
        dto.setUpdatedAt(bankDetails.getUpdatedAt());

        return dto;
    }
}
