package com.samyak.projectmanager.controller.ui;

import com.samyak.projectmanager.dto.request.LoginRequest;
import com.samyak.projectmanager.dto.request.RegisterRequest;
import com.samyak.projectmanager.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthUiController {

    private final AuthService authService;

    // ---------- SIGNUP ----------
    @PostMapping("/signup")
    public String handleSignup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
    ) {
        RegisterRequest request =
                new RegisterRequest(username, email, password);

        authService.register(request);
        return "redirect:/login";
    }

    // ---------- LOGIN ----------
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response
    ) {
        LoginRequest request =
                new LoginRequest(username, password);

        String jwt = authService.login(request);

        // NOTE:
        // For now, we DO NOT store JWT in cookie yet.
        // We only ensure UI flow works.
        // Dashboard integration comes next.

        return "redirect:/dashboard";
    }
}
