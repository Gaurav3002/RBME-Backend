package com.rbme.apis.controller.CompanySettings;

import com.rbme.apis.dto.CompanySettings.TaxInfoRequestDTO;
import com.rbme.apis.dto.CompanySettings.TaxInfoResponseDTO;
import com.rbme.apis.services.CompanySettings.TaxInfoDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.List;

@RestController
@RequestMapping("/api/admin/taxinfoDetails")
@RequiredArgsConstructor
public class TaxInfoDetailsController {

    private final TaxInfoDetailsImpl taxInfoDetails;

    @PreAuthorize("hasAuthority('TAX_GST_CREATE')")
    @PostMapping
    public ResponseEntity<TaxInfoResponseDTO> create(@RequestBody @Valid TaxInfoRequestDTO infoRequestDTO){
        TaxInfoResponseDTO responseDTO = taxInfoDetails.create(infoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PreAuthorize("hasAuthority('TAX_GST_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<TaxInfoResponseDTO> update(@PathVariable Long id, @RequestBody TaxInfoRequestDTO infoRequestDTO){
        TaxInfoResponseDTO responseDTO = taxInfoDetails.update(id,infoRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PreAuthorize("hasAuthority('TAX_GST_VIEW')")
    @GetMapping
    public ResponseEntity<List<TaxInfoResponseDTO>> getAllTaxInfo(){
        List<TaxInfoResponseDTO> responseDTO = taxInfoDetails.getAll();
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasAuthority('TAX_GST_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<TaxInfoResponseDTO> getAllTaxInfoById(@PathVariable long id){
        TaxInfoResponseDTO responseDTO = taxInfoDetails.getById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasAuthority('TAX_GST_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxInfoById(@PathVariable long id){
        taxInfoDetails.delete(id);
        return ResponseEntity.noContent().build();
    }

}
