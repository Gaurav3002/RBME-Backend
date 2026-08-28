package com.rbme.apis.controller;

import com.rbme.apis.dto.admin.AdminCreateRequest;
import com.rbme.apis.dto.admin.AdminLoginRequest;
import com.rbme.apis.dto.admin.AdminLoginResponse;
import com.rbme.apis.dto.admin.AdminResponse;
import com.rbme.apis.dto.admin.AdminUpdateRequest;
import com.rbme.apis.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminAuthController {

    private final AdminService adminService;


    @PostMapping("/auth/login")
    public ResponseEntity<AdminLoginResponse> login(
            @Valid @RequestBody AdminLoginRequest request) {

        return ResponseEntity.ok(adminService.login(request));
    }


    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(
            @Valid @RequestBody AdminCreateRequest request) {

        return new ResponseEntity<>(
                adminService.createAdmin(request),
                HttpStatus.CREATED
        );
    }


    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAllAdmins() {

        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    /**
     * Get Admin By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getAdminById(
            @PathVariable Long id) {

        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    /**
     * Update Admin
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateRequest request) {

        return ResponseEntity.ok(
                adminService.updateAdmin(id, request)
        );
    }

    /**
     * Activate / Deactivate Admin
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {

        adminService.updateStatus(id, active);

        return ResponseEntity.ok("Admin status updated successfully.");
    }

    /**
     * Delete Admin
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(
            @PathVariable Long id) {

        adminService.deleteAdmin(id);

        return ResponseEntity.ok("Admin deleted successfully.");
    }

}