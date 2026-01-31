package com.samyak.projectmanager.controller.auth;

import com.samyak.projectmanager.dto.request.LoginRequest;
import com.samyak.projectmanager.dto.request.RegisterRequest;
import com.samyak.projectmanager.dto.response.ApiResponse;
import com.samyak.projectmanager.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse<?>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {

        String token = authService.login(request);

        Cookie cookie = new Cookie("ACCESS_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true in production (HTTPS)
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 hour

        response.addCookie(cookie);

        return ResponseEntity.ok(
                ApiResponse.successMessage("Login successful")
        );
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
