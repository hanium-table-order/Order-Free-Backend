package com.example.tableorder.dto.realtime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 주문 상태 변경 실시간 이벤트 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangedEvent {

    private Long storeId;
    private Long tableId;
    private Long orderId;
    private String orderStatus; // "주문접수", "조리중", "완료", "취소" 등
    private String userName;
    private Long timestamp;
    private String message; // "~님의 주문이 준비 중입니다" 등
}
