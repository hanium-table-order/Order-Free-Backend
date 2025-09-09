package com.example.tableorder.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * JWT 토큰 검증 유틸 클래스.
 * - HS256 시크릿으로 서명 검증.
 * - issuer, 만료 시간, ROLE_OWNER 확인.
 * - 복잡하지 않게 최소 검증 로직만 구현.
 */
@Component
public class JwtDecoderUtil {

    private final SecretKey secretKey;
    private final String issuer;

    public JwtDecoderUtil(SecretKey secretKey, String issuer) {
        this.secretKey = secretKey;
        this.issuer = issuer;
    }

    /**
     * 토큰 검증 및 Authentication 객체 생성.
     * @param token Bearer 제거된 JWT 문자열
     * @return Authentication 객체 (ROLE_OWNER 포함)
     * @throws JwtException 검증 실패 시
     */
    public Authentication validateAndGetAuthentication(String token) throws JwtException {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // 만료 시간 체크 (이미 parser에서 처리되지만 명시)
        if (claims.getExpiration().before(new Date())) {
            throw new JwtException("토큰이 만료되었습니다.");
        }

        // 역할 확인 (claims에 "role"이 "OWNER"인지 가정)
        String role = claims.get("role", String.class);
        if (!"OWNER".equals(role)) {
            throw new JwtException("OWNER 역할이 아닙니다.");
        }

        // Authentication 생성 (principal은 subject, authorities는 ROLE_OWNER)
        String subject = claims.getSubject();
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_OWNER"));

        return new UsernamePasswordAuthenticationToken(subject, null, authorities);
    }
}