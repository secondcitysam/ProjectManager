package com.samyak.projectmanager.config.security;

import com.samyak.projectmanager.entity.User;
import com.samyak.projectmanager.exception.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AccessDeniedException("Unauthenticated request");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        throw new AccessDeniedException("Invalid authentication principal");
    }
}
