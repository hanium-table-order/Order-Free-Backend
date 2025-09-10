package com.example.tableorder.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * QR 코드 생성 요청 DTO.
 * - baseUrl, size optional.
 */
@Getter
@Setter
@Builder
public class QrCodeRequestDto {

    private String payloadBaseUrl;  // optional, default "https://app.example.com"

    @Positive
    private Integer size;  // optional, default 256
}