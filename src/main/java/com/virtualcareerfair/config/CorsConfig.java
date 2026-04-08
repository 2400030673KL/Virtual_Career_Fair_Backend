package com.virtualcareerfair.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${CORS_ORIGIN:}")
    private String corsOrigin;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowedOrigins = new ArrayList<>(Arrays.asList(
            "http://localhost:5173",
            "http://localhost:4173",
            "http://localhost:3000",
            "https://virtual-careeer-fair-v6nf.vercel.app",
            "https://*.vercel.app"
        ));

        if (corsOrigin != null && !corsOrigin.isBlank()) {
            for (String origin : corsOrigin.split(",")) {
                String trimmed = origin.trim();
                if (!trimmed.isEmpty()) {
                    allowedOrigins.add(trimmed);
                }
            }
        }

        configuration.setAllowedOriginPatterns(allowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
