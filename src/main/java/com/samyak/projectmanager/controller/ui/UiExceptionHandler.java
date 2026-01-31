package com.samyak.projectmanager.controller.ui;

import com.samyak.projectmanager.exception.AccessDeniedException;
import com.samyak.projectmanager.exception.ResourceNotFoundException;
import com.samyak.projectmanager.exception.UiBadRequestException;
import com.samyak.projectmanager.exception.UiNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice(basePackages = "com.samyak.projectmanager.controller.ui")
public class UiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(Model model) {
        model.addAttribute("errorTitle", "Access Denied");
        model.addAttribute("errorMessage", "You are not allowed to access this page.");
        model.addAttribute("actionText", "Go to Dashboard");
        model.addAttribute("actionUrl", "/dashboard");
        return "error/general-error";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model model) {
        model.addAttribute("errorTitle", "Not Found");
        model.addAttribute("errorMessage", "The requested resource does not exist.");
        model.addAttribute("actionText", "Go Back");
        model.addAttribute("actionUrl", "/dashboard");
        return "error/general-error";
    }

    @ExceptionHandler(UiNotFoundException.class)
    public String handleUiNotFound(
            UiNotFoundException ex,
            Model model
    ) {
        model.addAttribute("errorTitle", "Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("actionText", "Go Back");
        model.addAttribute("actionUrl", "javascript:history.back()");
        return "error/general-error";
    }

    @ExceptionHandler(UiBadRequestException.class)
    public String handleBadRequest(
            UiBadRequestException ex,
            Model model
    ) {
        model.addAttribute("errorTitle", "Action Not Allowed");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("actionText", "Go Back");
        model.addAttribute("actionUrl", "javascript:history.back()");
        return "error/general-error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Model model) {
        model.addAttribute("errorTitle", "Something went wrong");
        model.addAttribute("errorMessage", "Please try again later.");
        model.addAttribute("actionText", "Dashboard");
        model.addAttribute("actionUrl", "/dashboard");
        return "error/general-error";
    }
}
