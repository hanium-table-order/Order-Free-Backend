package com.example.tableorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tableorder.dto.MenuRequest;
import com.example.tableorder.dto.MenuResponse;
import com.example.tableorder.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}/tables/{tableId}/menus")
@Tag(name = "메뉴 관리", description = "메뉴 조회 및 관리 API")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "전체 메뉴 조회", description = "스토어의 모든 메뉴를 카테고리별로 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "메뉴 조회 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "스토어를 찾을 수 없음")

    })
    @GetMapping
    public ResponseEntity<MenuResponse> getMenus(
            @Parameter(description="스토어 ID",required = true,example ="1") @PathVariable Long storeId,
            @Parameter(description="테이블 ID",required = true,example ="10") @PathVariable Long tableId,
            @Parameter(description = "언어 코드 (ko, en, zh, ja 중 선택, 기본값=ko)", example = "en") @RequestParam(defaultValue = "ko") String lang) {

        log.info("메뉴 조회 요청: storeId={}, tableId={}, lang={}", storeId, tableId, lang);

        MenuResponse response = menuService.getMenus(storeId, tableId, lang);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카테고리별 메뉴 조회", description = "특정 카테고리의 메뉴만 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "카테고리별 메뉴 조회 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음")
    })
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<MenuResponse> getMenusByCategory(
            @Parameter(description = "스토어 ID", required = true, example = "1") @PathVariable Long storeId,
            @Parameter(description = "테이블 ID", required = true, example = "10") @PathVariable Long tableId,
            @Parameter(description = "카테고리 ID", required = true, example = "100") @PathVariable Long categoryId,
            @Parameter(description = "언어 코드 (ko, en, zh, ja 중 선택, 기본값=ko)", example = "ja") @RequestParam(defaultValue = "ko") String lang) {

        log.info("카테고리별 메뉴 조회 요청: storeId={}, tableId={}, categoryId={}, lang={}",
                storeId, tableId, categoryId, lang);

        MenuResponse response = menuService.getMenusByCategory(storeId, tableId, categoryId, lang);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "조건부 메뉴 조회", description = "categoryId가 있으면 해당 카테고리만, 없으면 전체 메뉴를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "메뉴 검색 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "스토어, 테이블 또는 카테고리를 찾을 수 없음")
    })
    @PostMapping("/search")
    public ResponseEntity<MenuResponse> searchMenus(
            @Parameter(description = "스토어 ID", required = true, example = "1") @PathVariable Long storeId,
            @Parameter(description = "테이블 ID", required = true, example = "10") @PathVariable Long tableId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "메뉴 검색 요청 DTO (categoryId, language 필드를 포함)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MenuRequest.class)))
            @RequestBody MenuRequest request) {

        log.info("메뉴 검색 요청: storeId={}, tableId={}, request={}", storeId, tableId, request);

        String lang = request.getLanguage() != null ? request.getLanguage() : "ko";

        MenuResponse response;
        if (request.getCategoryId() != null) {
            response = menuService.getMenusByCategory(storeId, tableId, request.getCategoryId(), lang);
        } else {
            response = menuService.getMenus(storeId, tableId, lang);
        }

        return ResponseEntity.ok(response);
    }

}
