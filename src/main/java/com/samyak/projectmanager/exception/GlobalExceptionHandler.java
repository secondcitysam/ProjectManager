package com.samyak.projectmanager.exception;

import com.samyak.projectmanager.dto.response.ApiError;
import com.samyak.projectmanager.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {
        com.samyak.projectmanager.controller.auth.AuthController.class,
        com.samyak.projectmanager.controller.team.TeamController.class
})
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        new ApiError("RESOURCE_NOT_FOUND", ex.getMessage())
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(
                        new ApiError("ACCESS_DENIED", ex.getMessage())
                ));
    }

    @ExceptionHandler(InvalidLifecycleActionException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidLifecycle(InvalidLifecycleActionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        new ApiError("INVALID_LIFECYCLE_ACTION", ex.getMessage())
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        new ApiError("VALIDATION_ERROR", message)
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        new ApiError("INTERNAL_ERROR", "Unexpected error occurred")
                ));
    }
}
