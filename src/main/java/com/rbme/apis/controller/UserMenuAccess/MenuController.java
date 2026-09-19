package com.rbme.apis.controller.UserMenuAccess;

import com.rbme.apis.dto.UserMenuAccess.MenuRequestDTO;
import com.rbme.apis.services.UserMenuAccess.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    // =========================================================
    // CREATE MENU
    // =========================================================

    @PreAuthorize("hasAuthority('MENU_CREATE')")
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody MenuRequestDTO dto
    ) {

        return ResponseEntity.ok(
                menuService.create(dto)
        );
    }

    // =========================================================
    // GET ALL MENUS - MANAGEMENT
    // =========================================================

    @PreAuthorize("hasAuthority('MENU_VIEW')")
    @GetMapping
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(
                menuService.getAll()
        );
    }

    // =========================================================
    // GET ACCESSIBLE MENU TREE
    // =========================================================
    //
    // This is used by the sidebar.
    //
    // It checks the user's actual permissions internally.
    //
    // =========================================================

    @GetMapping("/tree")
    public ResponseEntity<?> getTree() {

        return ResponseEntity.ok(
                menuService.getRootMenus()
        );
    }

    // =========================================================
    // DELETE / DEACTIVATE
    // =========================================================

    @PreAuthorize("hasAuthority('MENU_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ) {

        menuService.delete(id);

        return ResponseEntity.ok(
                "Menu deactivated successfully"
        );
    }
}