package com.example.tableorder.dto;

import java.util.List;

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
@Schema(description = "메뉴 아이템 정보", example = "아메리카노 메뉴 상세 정보")
public class MenuItemResponse {

    @NotNull
    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @NotNull
    @Schema(description = "메뉴명", example = "아메리카노")
    private String name;

    @Schema(description = "메뉴 설명", example = "깔끔한 아메리카노")
    private String description;

    @NotNull
    @Schema(description = "가격 (원)", example = "4500")
    private Integer price;

    @Schema(description = "이미지 URL", example = "https://example.com/americano.jpg")
    private String imageUrl;

    @NotNull
    @Schema(description = "품절 여부", example = "false")
    private Boolean soldOut;

    @Schema(description = "재고 수량", example = "100")
    private Integer quantity;

    @Schema(description = "재고 관리 사용 여부", example = "true")
    private Boolean enableInventory;

    @Schema(description = "준비 시간(분)", example = "3")
    private Integer prepTimeMin;

    @Schema(description = "메뉴 옵션 목록")
    private List<MenuOptionResponse> options;
}
