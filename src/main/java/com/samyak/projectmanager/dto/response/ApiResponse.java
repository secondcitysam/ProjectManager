package com.samyak.projectmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private ApiError error;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<?> successMessage(String message) {
        return new ApiResponse<>(true, message, null);
    }

    public static ApiResponse<?> error(ApiError error) {
        return new ApiResponse<>(false, null, error);
    }
}
