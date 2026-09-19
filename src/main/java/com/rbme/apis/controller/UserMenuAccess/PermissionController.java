package com.rbme.apis.controller.UserMenuAccess;

import com.rbme.apis.dto.UserMenuAccess.PermissionRequestDTO;
import com.rbme.apis.services.UserMenuAccess.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PermissionRequestDTO dto) {
        return ResponseEntity.ok(permissionService.create(dto));
    }

    @PreAuthorize("hasAuthority('PERMISSION_VIEW')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    @PreAuthorize("hasAuthority('PERMISSION_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getById(id));
    }

    @PreAuthorize("hasAuthority('PERMISSION_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PermissionRequestDTO dto) {
        return ResponseEntity.ok(permissionService.update(id, dto));
    }

    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return ResponseEntity.ok("Permission deleted successfully");
    }
}