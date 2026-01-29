package com.samyak.projectmanager.service.auth;

import com.samyak.projectmanager.dto.request.LoginRequest;
import com.samyak.projectmanager.dto.request.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    String login(LoginRequest request);

    void logout();
}
