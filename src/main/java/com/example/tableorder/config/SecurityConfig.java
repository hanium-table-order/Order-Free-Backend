package com.example.tableorder.config;

import com.example.tableorder.util.JwtDecoderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스.
 * - 모든 /api/** 엔드포인트에 JWT 인증 적용 (OWNER 역할 필수).
 * - 세션 사용 안 함 (STATELESS).
 * - JWT 필터 추가.
 * - Swagger UI 및 OpenAPI 엔드포인트 (/v3/api-docs/** 등) permitAll (테스트용, 프로덕션에서 제거 추천).
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true) // @PreAuthorize 활성화
public class SecurityConfig {

    private final JwtDecoderUtil jwtDecoderUtil;

    public SecurityConfig(JwtDecoderUtil jwtDecoderUtil) {
        this.jwtDecoderUtil = jwtDecoderUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // API 서버이므로 CSRF 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 미사용
                .authorizeHttpRequests(auth -> auth
                        // Swagger UI 및 OpenAPI 스펙 엔드포인트 permitAll 추가 (/v3/api-docs/** 포함)
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        // Static 파일 접근 허용 (웹소켓 테스트용)
                        .requestMatchers("/static/**", "/websocket-test.html", "/*.html", "/*.js", "/*.css").permitAll()
                        // 웹소켓 엔드포인트 허용
                        .requestMatchers("/ws/**", "/ws").permitAll()
                        // favicon.ico 허용
                        .requestMatchers("/favicon.ico").permitAll()
                        // 테스트용: 주문 및 장바구니 관련 API 임시 허용
                        .requestMatchers("/api/stores/*/tables/*/orders/**").permitAll()
                        .requestMatchers("/api/stores/*/tables/*/carts/**").permitAll()
                        .requestMatchers("/api/**").hasRole("OWNER") // 모든 API OWNER 권한 필요
                        .anyRequest().authenticated() // 기타 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtDecoderUtil), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

        return http.build();
    }
}