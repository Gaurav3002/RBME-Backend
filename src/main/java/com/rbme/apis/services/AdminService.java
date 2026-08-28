package com.rbme.apis.services;

import com.rbme.apis.dto.admin.AdminCreateRequest;
import com.rbme.apis.dto.admin.AdminLoginRequest;
import com.rbme.apis.dto.admin.AdminLoginResponse;
import com.rbme.apis.dto.admin.AdminResponse;
import com.rbme.apis.dto.admin.AdminUpdateRequest;

import java.util.List;

public interface AdminService {

    AdminLoginResponse login(AdminLoginRequest request);


    AdminResponse createAdmin(AdminCreateRequest request);


    AdminResponse updateAdmin(Long id, AdminUpdateRequest request);


    AdminResponse getAdminById(Long id);


    List<AdminResponse> getAllAdmins();


    void deleteAdmin(Long id);


    void updateStatus(Long id, Boolean active);
}