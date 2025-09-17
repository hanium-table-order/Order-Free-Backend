package com.example.tableorder.dto.realtime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "실시간 접속자 수 이벤트")
public class ViewerCountEvent {

    @Schema(description = "매장 ID", example = "1")
    private Long storeId;

    @Schema(description = "현재 접속자 수", example = "3")
    private Integer viewerCount;

    @Schema(description = "접속자 수 메시지", example = "현재 3명이 메뉴를 보고 있습니다")
    private String message;

    @Schema(description = "이벤트 발생 시간 (Unix timestamp)", example = "1703123456789")
    private Long timestamp;
}
