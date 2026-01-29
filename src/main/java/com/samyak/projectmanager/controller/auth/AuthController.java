package com.samyak.projectmanager.controller.auth;

import com.samyak.projectmanager.dto.request.LoginRequest;
import com.samyak.projectmanager.dto.request.RegisterRequest;
import com.samyak.projectmanager.dto.response.ApiResponse;
import com.samyak.projectmanager.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ApiResponse.successMessage("User registered successfully");
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout() {
        authService.logout();
        return ApiResponse.successMessage("Logged out successfully");
    }
}
