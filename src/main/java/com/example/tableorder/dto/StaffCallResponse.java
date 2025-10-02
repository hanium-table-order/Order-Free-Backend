package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "직원 호출 응답(손님)")
public class StaffCallResponse {
    @Schema(description = "직원 호출 타입 ID", example="1")
    private Long id;

    @Schema(description = "직원 호출 하는 테이블 ID", example="5")
    private Long tableId;
    @Schema(description = "호출 메시지", example = "물 주세요")
    private String message;
    @Schema(description = "호출 시각", example = "2024-01-15T14:30:25")
    private LocalDateTime requestedAt;

}
