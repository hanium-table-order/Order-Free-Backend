package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * QR 코드 응답 DTO.
 * - payloadUrl과 Base64 이미지 포함.
 */
@Getter
@Setter
@Builder
public class QrCodeResponseDto {

    private Long storeId;

    private Long tableId;

    private String payloadUrl;  // e.g., https://app.example.com/stores/{storeId}/tables/{tableId}

    private String imageBase64;  // PNG Base64

    private Instant expiresAt;  // 캐시 만료 시간 (nullable)
}