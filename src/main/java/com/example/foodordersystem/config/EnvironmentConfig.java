package com.example.foodordersystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
@Validated
public class EnvironmentConfig {

    private OpenApiConfig openapi = new OpenApiConfig();
    private CorsConfig cors = new CorsConfig();
    private RateLimitConfig rateLimit = new RateLimitConfig();

    @Data
    public static class OpenApiConfig {
        @NotBlank
        private String devUrl;
        @NotBlank
        private String prodUrl;
    }

    @Data
    public static class CorsConfig {
        @NotEmpty
        private List<String> allowedOrigins;
        @NotEmpty
        private List<String> allowedMethods;
        @NotEmpty
        private List<String> allowedHeaders;
        private boolean allowCredentials = true;
    }

    @Data
    public static class RateLimitConfig {
        private boolean enabled = false;
        private int requestsPerMinute = 100;
    }
}

