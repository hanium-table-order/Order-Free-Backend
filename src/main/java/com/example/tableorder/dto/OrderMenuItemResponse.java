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
@Schema(description = "주문 내역용 메뉴 아이템 정보")
public class OrderMenuItemResponse {

    @NotNull
    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @NotNull
    @Schema(description = "메뉴명", example = "김치찌개")
    private String name;

    @Schema(description = "메뉴 설명", example = "매콤한 김치와 돼지고기로 만든 찌개")
    private String description;

    @NotNull
    @Schema(description = "가격 (원)", example = "15000")
    private Integer price;
}
