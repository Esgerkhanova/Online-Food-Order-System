package com.example.foodordersystem.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standart error response structure")
public class ErrorResponse {

    @Schema(description = "Error mesajı", example = "User not found")
    private String message;

    @Schema(description = "Error kodu", example = "USER_NOT_FOUND")
    private String errorCode;

    @Schema(description = "HTTP status kodu", example = "404")
    private int status;

    @Schema(description = "Request path", example = "/api/users/123")
    private String path;

    @Schema(description = "Error baş verən vaxt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Validation error-ları")
    private List<ValidationError> validationErrors;


    public ErrorResponse(String message, String errorCode, int status, String path) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
    }
}

