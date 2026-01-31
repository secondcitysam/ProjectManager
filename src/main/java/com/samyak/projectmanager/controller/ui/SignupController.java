package com.samyak.projectmanager.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String signupPage() {
        return "auth/signup";
    }
}
