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
@Schema(description = "카테고리별 메뉴 정보")
public class CategoryMenuResponse {

    @NotNull
    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @NotNull
    @Schema(description = "카테고리명", example = "음료")
    private String categoryName;

    @NotNull
    @Schema(description = "해당 카테고리의 메뉴 목록")
    private List<MenuItemResponse> menuItems;
}
