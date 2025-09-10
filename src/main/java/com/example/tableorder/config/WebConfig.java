package com.example.tableorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 MVC 설정 클래스.
 * - CORS 허용 (모든 오리진/메서드, 패턴으로 * 지원).
 * - allowCredentials(true) 시 * 사용 위해 allowedOriginPatterns 적용.
 * - 필요 시 Interceptor 추가 가능.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:8080")  // * → allowedOriginPatterns("*")로 변경 (credentials true 시 안전)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}