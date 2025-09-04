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
@Schema(description = "메뉴 조회 응답", example = "스토어의 전체 메뉴 정보")
public class MenuResponse {

    @NotNull
    @Schema(description = "스토어 ID", example = "1")
    private Long storeId;

    @NotNull
    @Schema(description = "테이블 ID", example = "10")
    private Long tableId;

    @Schema(description = "응답 언어", example = "ko", allowableValues = {"ko", "en", "zh", "ja"})
    private String language;

    @NotNull
    @Schema(description = "카테고리별 메뉴 목록")
    private List<CategoryMenuResponse> categories;
}
