package com.cardekho.recommendation.dto;

import java.time.Instant;
import java.util.Map;

public record ApiResponse<T>(
        boolean success,
        T data,
        ErrorDetails error,
        Map<String, Object> meta
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, Map.of("timestamp", Instant.now()));
    }

    public static <T> ApiResponse<T> failure(String code, String message) {
        return new ApiResponse<>(false, null, new ErrorDetails(code, message), Map.of("timestamp", Instant.now()));
    }
}
