package com.example.tableorder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tableorder.dto.OrderResponse;
import com.example.tableorder.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stores/{storeId}/tables/{tableId}/orders")
@RequiredArgsConstructor
@Tag(name = "주문", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(
            summary = "주문 생성",
            description = "장바구니에 있는 상품들을 주문으로 생성합니다. 주문 후 장바구니는 자동으로 비워집니다."
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "주문이 성공적으로 생성됨",
                content = @Content(schema = @Schema(implementation = OrderResponse.class))
        ),
        @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (장바구니가 비어있음, 주문할 상품이 없음 등)"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "테이블을 찾을 수 없음"
        )
    })
    public ResponseEntity<OrderResponse> createOrder(
            @Parameter(description = "매장 ID", example = "1") @PathVariable Long storeId,
            @Parameter(description = "테이블 ID", example = "5") @PathVariable Long tableId) {

        try {
            OrderResponse response = orderService.createOrder(storeId, tableId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
