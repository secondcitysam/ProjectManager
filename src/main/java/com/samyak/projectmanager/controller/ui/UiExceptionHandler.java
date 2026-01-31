package com.samyak.projectmanager.controller.ui;

import com.samyak.projectmanager.exception.DuplicateResourceException;
import com.samyak.projectmanager.exception.InvalidCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.samyak.projectmanager.controller.ui")
public class UiExceptionHandler {

    // -------- SIGNUP ERRORS --------
    @ExceptionHandler(DuplicateResourceException.class)
    public String handleDuplicate(
            DuplicateResourceException ex,
            Model model
    ) {
        model.addAttribute("errorTitle", "Signup Failed");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("actionUrl", "/signup");
        model.addAttribute("actionText", "Try Again");
        return "error/general-error";
    }

    // -------- LOGIN ERRORS --------
    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleLoginError(
            InvalidCredentialsException ex,
            Model model
    ) {
        model.addAttribute("errorTitle", "Login Failed");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("actionUrl", "/login");
        model.addAttribute("actionText", "Back to Login");
        return "error/general-error";
    }

    // -------- FALLBACK --------
    @ExceptionHandler(Exception.class)
    public String handleGeneric(
            Exception ex,
            Model model
    ) {
        model.addAttribute("errorTitle", "Unexpected Error");
        model.addAttribute(
                "errorMessage",
                "Something went wrong. Please try again later."
        );
        model.addAttribute("actionUrl", "/");
        model.addAttribute("actionText", "Go to Home");
        return "error/general-error";
    }
}
