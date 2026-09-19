package com.rbme.apis.services;

import com.rbme.apis.dto.UserMenuAccess.LoginRequestDTO;
import com.rbme.apis.dto.UserMenuAccess.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
}
