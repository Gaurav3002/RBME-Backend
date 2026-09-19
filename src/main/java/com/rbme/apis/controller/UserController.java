package com.rbme.apis.controller;

import com.rbme.apis.dto.UserMenuAccess.UserRequestDTO;
import com.rbme.apis.dto.UserMenuAccess.UserResponseDTO;
import com.rbme.apis.services.UserMenuAccess.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;


    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO request) {

        return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED
        );
    }


    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAll()
        );
    }



    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                userService.getById(id)
        );
    }


    @PreAuthorize("hasAuthority('USER_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO request
    ) {

        return ResponseEntity.ok(
                userService.update(id, request)
        );
    }


    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {

        userService.delete(id);

        return ResponseEntity.ok(
                "User deleted successfully."
        );
    }
}