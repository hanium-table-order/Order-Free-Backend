package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "장바구니 아이템 추가 요청")
public class CartItemAddRequest {

    @Schema(description = "메뉴 아이템 ID", example = "123", required = true)
    private Long menuItemId;

    @Schema(description = "수량", example = "2", required = true, minimum = "1")
    private Integer quantity;

    @Schema(description = "언어 코드", example = "ko", defaultValue = "ko")
    private String lang;
}
