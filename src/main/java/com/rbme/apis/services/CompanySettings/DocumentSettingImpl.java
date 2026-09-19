package com.rbme.apis.services.CompanySettings;

import com.rbme.apis.dto.CompanySettings.DocumentSettingsRequestDTO;
import com.rbme.apis.dto.CompanySettings.DocumentSettingsResponseDTO;
import com.rbme.apis.entity.CompanySettings.DocumentSettings;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.CompanySetting.DocumentSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentSettingImpl {

    private final DocumentSettingsRepository repository;


    public DocumentSettingsResponseDTO create(DocumentSettingsRequestDTO dto) {

        if (dto == null) {
            throw new ResourceNotFoundException("Request cannot be empty");
        }

        DocumentSettings documentSettings = new DocumentSettings();

        documentSettings.setDocumentType(dto.getDocumentType());
        documentSettings.setHeaderText(dto.getHeaderText());
        documentSettings.setFooterText(dto.getFooterText());
        documentSettings.setTermAndCondition(dto.getTermAndCondition());
        documentSettings.setInstructions(dto.getInstructions());
        DocumentSettings saved = repository.save(documentSettings);
        return mapToResponseDto(saved);
    }

    public DocumentSettingsResponseDTO update(Long id, DocumentSettingsRequestDTO dto) {

        if (dto == null) {
            throw new ResourceNotFoundException("Request cannot be empty");
        }

        DocumentSettings documentSettings = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document settings not found with id: " + id));

        documentSettings.setDocumentType(dto.getDocumentType());
        documentSettings.setHeaderText(dto.getHeaderText());
        documentSettings.setFooterText(dto.getFooterText());
        documentSettings.setTermAndCondition(dto.getTermAndCondition());
        documentSettings.setInstructions(dto.getInstructions());

        DocumentSettings saved = repository.save(documentSettings);

        return mapToResponseDto(saved);
    }


    public List<DocumentSettingsResponseDTO> getAllDocuments() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }


    public DocumentSettingsResponseDTO getById(Long id) {

        DocumentSettings documentSettings = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document settings not found with id: " + id));

        return mapToResponseDto(documentSettings);
    }


    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Document settings not found with id: " + id);
        }

        repository.deleteById(id);
    }


    // ============================
    // MAP ENTITY → RESPONSE DTO
    // ============================
    private DocumentSettingsResponseDTO mapToResponseDto(
            DocumentSettings documentSettings) {

        DocumentSettingsResponseDTO response =
                new DocumentSettingsResponseDTO();

        response.setId(documentSettings.getId());

        if (documentSettings.getDocumentType() != null) {
            response.setDocumentType(
                    documentSettings.getDocumentType()
            );
        }

        response.setHeaderText(documentSettings.getHeaderText());
        response.setFooterText(documentSettings.getFooterText());
        response.setTermAndCondition(
                documentSettings.getTermAndCondition()
        );
        response.setInstructions(
                documentSettings.getInstructions()
        );
        return response;
    }
}
