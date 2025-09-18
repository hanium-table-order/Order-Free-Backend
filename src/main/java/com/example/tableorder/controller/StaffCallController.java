package com.example.tableorder.controller;

import com.example.tableorder.dto.StaffCallTypeRequest;
import com.example.tableorder.dto.StaffCallTypeResponse;
import com.example.tableorder.service.StaffCallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores/{storeId}")
@RequiredArgsConstructor
@Tag(name = "직원 호출", description = "직원 호출 관련 API")
public class StaffCallController {

    private final StaffCallService staffCallService;

    @PostMapping("/staff-call-types")
    @Operation(
            summary = "직원 호출 타입 추가",
            description = "점주가 새로운 직원 호출 타입을 추가할 수 있습니다. 예: '물 주세요', '계산해주세요' 등",
            tags = {"직원 호출 관리"}
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "직원 호출 타입이 성공적으로 생성됨",
                content = @io.swagger.v3.oas.annotations.media.Content(
                        mediaType = "application/json",
                        schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = StaffCallTypeResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 데이터 (메시지가 비어있음)"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "해당 매장을 찾을 수 없음"
        )
    })
    public ResponseEntity<StaffCallTypeResponse> addStaffCallType(
            @Parameter(
                    description = "매장 ID",
                    required = true,
                    example = "1",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(type = "integer", format = "int64")
            )
            @PathVariable Long storeId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "직원 호출 타입 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = StaffCallTypeRequest.class)
                    )
            )
            @Valid @RequestBody StaffCallTypeRequest request) {

        try {
            StaffCallTypeResponse response = staffCallService.staffCallTypeCreate(storeId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
