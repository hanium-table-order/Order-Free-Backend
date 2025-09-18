package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "직원 호출 타입 생성 요청")
public class StaffCallTypeRequest {

    @Schema(
            description = "호출 메시지",
            example = "물 주세요",
            required = true
    )
    @NotBlank(message = "호출 메시지는 필수입니다")
    private String message;
}
