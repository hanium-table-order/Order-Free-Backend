package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "직원 호출 타입 응답")
public class StaffCallTypeResponse {

    @Schema(description = "직원 호출 타입 ID", example = "1")
    private Long id;

    @Schema(description = "호출 메시지", example = "물 주세요")
    private String message;

    @Schema(description = "활성화 상태", example = "true")
    private boolean active;
}
