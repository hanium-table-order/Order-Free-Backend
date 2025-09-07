package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 아이템 상세 정보")
public class OrderItemDetailResponse {

    @NotNull
    @Schema(description = "메뉴 정보")
    private MenuItemResponse menu;

    @NotNull
    @Schema(description = "주문 수량", example = "2")
    private Integer quantity;

    @Schema(description = "메뉴 옵션 목록")
    private List<MenuOptionResponse> options;

    @NotNull
    @Schema(description = "해당 아이템 소계 (원)", example = "10000")
    private Integer subtotal;
}
