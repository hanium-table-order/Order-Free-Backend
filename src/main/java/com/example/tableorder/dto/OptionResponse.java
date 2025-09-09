package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "옵션 응답")
public class OptionResponse {

    @Schema(description = "옵션 ID", example = "1")
    private Long optionId;

    @Schema(description = "옵션 이름", example = "밥 추가")
    private String optionName;

    @Schema(description = "추가 가격", example = "2000")
    private Integer extraPrice;

    @Schema(description = "옵션 수량", example = "2")
    private Integer quantity;
}
