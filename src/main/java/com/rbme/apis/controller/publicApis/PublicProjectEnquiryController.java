package com.rbme.apis.controller.publicApis;

import com.rbme.apis.dto.admin.ProjectEnquiryRequest;
import com.rbme.apis.dto.admin.ProjectEnquiryResponse;
import com.rbme.apis.services.ProjectEnquiryService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-enquiries")
@CrossOrigin
@RequiredArgsConstructor
public class PublicProjectEnquiryController {

    private final ProjectEnquiryService enquiryService;




    @PostMapping
    public ResponseEntity<ProjectEnquiryResponse> createEnquiry(@Valid @RequestBody ProjectEnquiryRequest request) {
        ProjectEnquiryResponse response = enquiryService.createEnquiry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /* =========================================================
       GET ALL
    ========================================================= */

    @GetMapping
    public ResponseEntity<List<ProjectEnquiryResponse>> getAllEnquiries() {
        return ResponseEntity.ok(enquiryService.getAllEnquiries());
    }


    /* =========================================================
       GET BY ID
    ========================================================= */

    @GetMapping("/{id}")
    public ResponseEntity<ProjectEnquiryResponse> getEnquiryById(@PathVariable Long id) {
        return ResponseEntity.ok(enquiryService.getEnquiryById(id));
    }


    /* =========================================================
       GET BY COMPANY
    ========================================================= */

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ProjectEnquiryResponse>> getByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(enquiryService.getEnquiriesByCompany(companyId));
    }


    /* =========================================================
       GET BY STATUS
    ========================================================= */

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectEnquiryResponse>> getByStatus(
            @PathVariable String status
    ) {

        return ResponseEntity.ok(
                enquiryService.getEnquiriesByStatus(status)
        );
    }


    /* =========================================================
       UPDATE STATUS
    ========================================================= */

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProjectEnquiryResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {

        return ResponseEntity.ok(
                enquiryService.updateStatus(id, status)
        );
    }
}