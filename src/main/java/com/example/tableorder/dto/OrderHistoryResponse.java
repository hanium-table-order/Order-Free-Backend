package com.example.tableorder.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderHistoryResponse {

    private Long orderId; // 주문 번호
    private LocalDateTime orderTime; // 주문 시간
    private String orderStatus; // 주문 상태(주문 접수, 조리중, 완료)
    private Integer totalItemCount; // 총 주문 건 수
    private Integer totalAmount; // 총 결제 금액

    private List<OrderItemDetailResponse> orderItems;
}
