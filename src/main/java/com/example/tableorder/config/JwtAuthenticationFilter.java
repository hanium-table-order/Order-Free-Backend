package com.example.tableorder.config;

import com.example.tableorder.util.JwtDecoderUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 인증 필터 클래스.
 * - 요청 헤더에서 Bearer 토큰 추출.
 * - JwtDecoderUtil로 검증 후 SecurityContext 설정.
 * - 실패 시 401 응답 (예외 처리).
 * - 복잡하지 않게 최소 필터 로직만 구현.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoderUtil jwtDecoderUtil;

    public JwtAuthenticationFilter(JwtDecoderUtil jwtDecoderUtil) {
        this.jwtDecoderUtil = jwtDecoderUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Bearer 제거
            try {
                Authentication authentication = jwtDecoderUtil.validateAndGetAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 검증 실패 시 SecurityContext 비움 (SecurityConfig에서 401/403 처리)
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}