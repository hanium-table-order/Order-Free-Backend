package com.example.tableorder.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "직원 호출 요청")
public class StaffCallRequest {
    @Schema(description="테이블 ID", example="5")
    @NotNull(message = "테이블 ID는 필수입니다")
    private Long tableId;
    
    @Schema(description="직원 호출 타입 ID",example="1")
    @NotNull(message = "직원 호출 타입 ID는 필수입니다")
    private Long callTypeId;
}
