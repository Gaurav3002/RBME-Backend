package com.rbme.apis.services.CompanySettings;

import com.rbme.apis.constants.Helper;
import com.rbme.apis.dto.CompanySettings.TaxInfoRequestDTO;
import com.rbme.apis.dto.CompanySettings.TaxInfoResponseDTO;
import com.rbme.apis.entity.CompanySettings.TaxInfoDetails;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.CompanySetting.TaxInfoDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxInfoDetailsImpl {

    private final TaxInfoDetailsRepository taxInfoDetailsRepository;


    public TaxInfoResponseDTO create(TaxInfoRequestDTO infoRequestDTO) {

        if (infoRequestDTO == null) {
            throw new ResourceNotFoundException("Request cannot be null");
        }

        if (infoRequestDTO.getTaxName() == null || infoRequestDTO.getTaxName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Tax name is mandatory");
        }

        String taxName = infoRequestDTO.getTaxName().trim();

        if (taxInfoDetailsRepository.existsByTaxName(taxName)) {
            throw new RuntimeException("Tax already exists with name: " + taxName);
        }

        TaxInfoDetails taxInfoDetails = new TaxInfoDetails();
        taxInfoDetails.setTaxName(taxName);
        taxInfoDetails.setTaxPercentage(infoRequestDTO.getTaxPercentage());
        taxInfoDetails.setCreatedOn(LocalDateTime.now());
        taxInfoDetails.setCreatedBy(Helper.getLoginUserId());
        TaxInfoDetails savedTaxInfo = taxInfoDetailsRepository.save(taxInfoDetails);
        return convertToResponse(savedTaxInfo);
    }


    public TaxInfoResponseDTO getById(Long id) {

        TaxInfoDetails taxInfoDetails = taxInfoDetailsRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Tax details not exist with this id: " + id));
        return convertToResponse(taxInfoDetails);
    }


    public List<TaxInfoResponseDTO> getAll() {

        return taxInfoDetailsRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }


    public TaxInfoResponseDTO update(Long id, TaxInfoRequestDTO infoRequestDTO) {

        if (infoRequestDTO == null) {
            throw new ResourceNotFoundException("Request cannot be null");
        }

        TaxInfoDetails taxInfoDetails = taxInfoDetailsRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Tax details not exist with this id: " + id));

        if (infoRequestDTO.getTaxName() == null || infoRequestDTO.getTaxName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Tax name is mandatory");
        }

        String taxName = infoRequestDTO.getTaxName().trim();

        if (taxInfoDetailsRepository.existsByTaxNameAndIdNot(taxName, id)) {
            throw new RuntimeException("Tax already exists with name: " + taxName);
        }

        taxInfoDetails.setTaxName(taxName);
        taxInfoDetails.setTaxPercentage(infoRequestDTO.getTaxPercentage()
        );

        TaxInfoDetails updatedTaxInfo = taxInfoDetailsRepository.save(taxInfoDetails);
        return convertToResponse(updatedTaxInfo);
    }

    public void delete(Long id) {

        TaxInfoDetails taxInfoDetails = taxInfoDetailsRepository.findById(id).orElseThrow(() ->
                                new ResourceNotFoundException("Tax details not exist with this id: " + id));
        taxInfoDetailsRepository.delete(taxInfoDetails);
    }


    private TaxInfoResponseDTO convertToResponse(TaxInfoDetails taxInfoDetails) {

        TaxInfoResponseDTO response = new TaxInfoResponseDTO();
        response.setId(taxInfoDetails.getId());
        response.setTaxName(taxInfoDetails.getTaxName());
        response.setTaxPercentage(taxInfoDetails.getTaxPercentage());
        response.setCreatedOn(taxInfoDetails.getCreatedOn());
        response.setCreatedBy(taxInfoDetails.getCreatedBy());
        return response;
    }


}