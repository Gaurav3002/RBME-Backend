package com.rbme.apis.controller.CompanySettings;

import com.rbme.apis.dto.CompanySettings.BankDetailsRequestDto;
import com.rbme.apis.dto.CompanySettings.BankDetailsResponseDto;
import com.rbme.apis.services.CompanySettings.BankDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bankdetails")
@RequiredArgsConstructor
public class BankDetailsController {

    private final BankDetailsImpl bankDetailsService;



    @PreAuthorize("hasAuthority('CREATE_BANKDETAILS')")
    @PostMapping
    public ResponseEntity<BankDetailsResponseDto> createBankDetails(@RequestBody BankDetailsRequestDto dto) {

        BankDetailsResponseDto response = bankDetailsService.createBankDetails(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


        @PreAuthorize("hasAuthority('VIEW_BANKDETAILS')")
    @GetMapping("/{id}")
    public ResponseEntity<BankDetailsResponseDto> getBankDetailsById(@PathVariable Long id) {
        BankDetailsResponseDto response = bankDetailsService.getBankDetailsById(id);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('VIEW_BANKDETAILS')")
    @GetMapping
    public ResponseEntity<List<BankDetailsResponseDto>> getAllBankDetails() {
        List<BankDetailsResponseDto> response = bankDetailsService.getAllBankDetails();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('UPDATE_BANKDETAILS')")
    @PutMapping("/{id}")
    public ResponseEntity<BankDetailsResponseDto> updateBankDetails(@PathVariable Long id, @RequestBody BankDetailsRequestDto dto) {
        BankDetailsResponseDto response = bankDetailsService.updateBankDetails(id, dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('DELETE_BANKDETAILS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankDetails(@PathVariable Long id) {
        bankDetailsService.deleteBankDetails(id);
        return ResponseEntity.noContent().build();
    }
}
