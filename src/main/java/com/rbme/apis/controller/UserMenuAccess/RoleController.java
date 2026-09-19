package com.rbme.apis.controller.UserMenuAccess;

import com.rbme.apis.dto.UserMenuAccess.RoleRequestDTO;
import com.rbme.apis.dto.UserMenuAccess.RoleResponseDTO;
import com.rbme.apis.services.UserMenuAccess.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // =========================================================
    // CREATE ROLE
    // =========================================================

    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping
    public ResponseEntity<RoleResponseDTO> create(
            @RequestBody RoleRequestDTO dto
    ) {

        return new ResponseEntity<>(
                roleService.create(dto),
                HttpStatus.CREATED
        );
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @PreAuthorize("hasAuthority('ROLE_VIEW')")
    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAll() {

        return ResponseEntity.ok(
                roleService.getAll()
        );
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @PreAuthorize("hasAuthority('ROLE_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                roleService.getById(id)
        );
    }

    // =========================================================
    // UPDATE ROLE
    // =========================================================

    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> update(
            @PathVariable Long id,
            @RequestBody RoleRequestDTO dto
    ) {

        return ResponseEntity.ok(
                roleService.update(id, dto)
        );
    }

    // =========================================================
    // DELETE ROLE
    // =========================================================

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {

        roleService.delete(id);

        return ResponseEntity.ok(
                "Role deactivated successfully"
        );
    }

    // =========================================================
    // ASSIGN ONE PERMISSION
    // =========================================================

    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<RoleResponseDTO> assignPermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId
    ) {

        return ResponseEntity.ok(
                roleService.assignPermission(
                        roleId,
                        permissionId
                )
        );
    }

    // =========================================================
    // REMOVE ONE PERMISSION
    // =========================================================

    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<RoleResponseDTO> removePermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId
    ) {

        return ResponseEntity.ok(
                roleService.removePermission(
                        roleId,
                        permissionId
                )
        );
    }
}