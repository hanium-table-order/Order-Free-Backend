package com.example.tableorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "메뉴 조회 요청")
public class MenuRequest {

    @Schema(description = "스토어 ID", example = "1")
    private Long storeId;

    @Schema(description = "테이블 ID", example = "5")
    private Long tableId;

    @Schema(description = "언어 코드 (ko, en, zh, ja)", example = "ko")
    private String language;

    @Schema(description = "카테고리 ID (선택사항, 없으면 전체 메뉴 조회)", example = "2")
    private Long categoryId;
}
