package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDetailResponse {

    // 장바구니에 담긴 아이템들
    private List<Item> items;

    // 장바구니 총 가격
    private Integer cartTotalPrice;

    @Data
    @Builder
    public static class Item {
        private Long cartItemId;
        private Long menuItemId;
        private String menuName;
        private Integer quantity;
        private Integer price;     // 단가
        private Integer linePrice; // 단가 * 수량
    }
}