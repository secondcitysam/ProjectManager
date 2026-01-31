package com.samyak.projectmanager.controller.auth;

import com.samyak.projectmanager.dto.request.LoginRequest;
import com.samyak.projectmanager.dto.request.RegisterRequest;
import com.samyak.projectmanager.dto.response.ApiResponse;
import com.samyak.projectmanager.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

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
    public ApiResponse<?> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        String jwt = authService.login(request);

        // ✅ SET JWT AS HTTP-ONLY COOKIE (UI SUPPORT)
        ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", jwt)
                .httpOnly(true)
                .secure(false) // true in prod (HTTPS)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.success(jwt);
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletResponse response) {

        // Clear cookie
        ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.successMessage("Logged out successfully");
    }
}
