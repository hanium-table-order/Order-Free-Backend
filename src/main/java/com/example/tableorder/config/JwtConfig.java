package com.example.tableorder.config;

import com.example.tableorder.util.JwtDecoderUtil;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * JWT 설정 클래스.
 * - HS256 시크릿 키 생성.
 * - JwtDecoder 유틸 제공 (토큰 검증용).
 */
@Configuration
public class JwtConfig {

    private static final Logger log = LoggerFactory.getLogger(JwtConfig.class);

    @Value("${spring.security.jwt.secret:defaultSecretForTestingChangeMe}")  // 임시 defaultValue 추가 (디버깅용, .env 로드 성공 시 제거)
    private String jwtSecret;

    @Value("${spring.security.jwt.issuer}")
    private String issuer;

    @PostConstruct
    public void init() {
        log.info("JWT Secret loaded: {} (length: {})", jwtSecret.substring(0, 5) + "...", jwtSecret.length());  // 로드 확인 로그 (민감 정보 마스킹)
    }

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public JwtDecoderUtil jwtDecoderUtil() {
        return new JwtDecoderUtil(jwtSecretKey(), issuer);
    }
}