package com.example.foodordersystem.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final EnvironmentConfig environmentConfig;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allowed origins from environment config
        configuration.setAllowedOriginPatterns(environmentConfig.getCors().getAllowedOrigins());

        // Allowed methods
        configuration.setAllowedMethods(environmentConfig.getCors().getAllowedMethods());

        // Allowed headers
        configuration.setAllowedHeaders(environmentConfig.getCors().getAllowedHeaders());

        // Allow credentials
        configuration.setAllowCredentials(environmentConfig.getCors().isAllowCredentials());

        // Exposed headers
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Cache-Control",
                "Content-Type",
                "X-Total-Count",
                "X-Page-Number",
                "X-Page-Size"
        ));

        // Max age for preflight requests
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
