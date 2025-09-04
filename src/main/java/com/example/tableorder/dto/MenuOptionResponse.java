package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "메뉴 옵션 정보", example = "샷 추가 옵션")
public class MenuOptionResponse {

    @NotNull
    @Schema(description = "옵션 ID", example = "1")
    private Long optionId;

    @NotNull
    @Schema(description = "옵션명", example = "샷 추가")
    private String name;

    @Schema(description = "옵션 설명", example = "에스프레소 샷을 추가합니다")
    private String description;

    @NotNull
    @Schema(description = "추가 가격 (원)", example = "500")
    private Integer price;

    @NotNull
    @Schema(description = "필수 여부", example = "false")
    private Boolean required;
}
