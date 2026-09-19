package com.rbme.apis.controller.CompanySettings;

import com.rbme.apis.dto.CompanySettings.DocumentSettingsRequestDTO;
import com.rbme.apis.dto.CompanySettings.DocumentSettingsResponseDTO;
import com.rbme.apis.services.CompanySettings.DocumentSettingImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/document-settings")
@RequiredArgsConstructor
public class DocumentSettingController {

    private final DocumentSettingImpl impl;


    @PostMapping
    @PreAuthorize("hasAuthority('DOCUMENT_SETTING_CREATE')")
    public ResponseEntity<DocumentSettingsResponseDTO> createDocument(@RequestBody @Valid DocumentSettingsRequestDTO dto) {

        DocumentSettingsResponseDTO response = impl.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('DOCUMENT_SETTING_VIEW')")
    public ResponseEntity<List<DocumentSettingsResponseDTO>> getAllDocuments() {
        List<DocumentSettingsResponseDTO> response = impl.getAllDocuments();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DOCUMENT_SETTING_VIEW')")
    public ResponseEntity<DocumentSettingsResponseDTO> getDocumentById(@PathVariable Long id) {

        DocumentSettingsResponseDTO response = impl.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DOCUMENT_SETTING_UPDATE')")
    public ResponseEntity<DocumentSettingsResponseDTO> updateDocument(@PathVariable Long id, @RequestBody @Valid DocumentSettingsRequestDTO dto) {
        DocumentSettingsResponseDTO response = impl.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DOCUMENT_SETTING_DELETE')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        impl.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

