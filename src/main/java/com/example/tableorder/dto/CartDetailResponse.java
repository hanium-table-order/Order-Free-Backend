package com.example.tableorder.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "장바구니 상세 조회 응답")
public class CartDetailResponse {

    @Schema(description = "장바구니에 담긴 아이템들")
    private List<Item> items;

    @Schema(description = "장바구니 총 가격", example = "25000")
    private Integer cartTotalPrice;

    @Data
    @Builder
    @Schema(description = "장바구니 아이템")
    public static class Item {

        @Schema(description = "장바구니 아이템 ID", example = "456")
        private Long cartItemId;

        @Schema(description = "메뉴 아이템 ID", example = "123")
        private Long menuItemId;

        @Schema(description = "메뉴 이름", example = "치킨버거")
        private String menuName;

        @Schema(description = "수량", example = "2")
        private Integer quantity;

        @Schema(description = "단가", example = "8000")
        private Integer price;

        @Schema(description = "총 가격 (단가 × 수량)", example = "16000")
        private Integer linePrice;
    }
}
