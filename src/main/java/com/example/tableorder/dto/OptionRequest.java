package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "옵션 요청")
public class OptionRequest {

    @Schema(description = "옵션 ID", example = "1", required = true)
    @NotNull(message = "옵션 ID는 필수입니다")
    private Long optionId;

    @Schema(description = "옵션 수량", example = "2", required = true)
    @NotNull(message = "옵션 수량은 필수입니다")
    @Positive(message = "옵션 수량은 1 이상이어야 합니다")
    private Integer quantity;
}
