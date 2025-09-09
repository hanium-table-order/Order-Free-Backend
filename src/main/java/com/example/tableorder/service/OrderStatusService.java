package com.example.tableorder.service;

import com.example.tableorder.dto.order.UpdateOrderStatusResponse;
import com.example.tableorder.entity.order.Order;
import com.example.tableorder.exception.BadRequestException;
import com.example.tableorder.exception.NotFoundException;
import com.example.tableorder.repository.OrderStatusRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Transactional
    public UpdateOrderStatusResponse updateOrderStatus(Long orderId, String status) {
        Order o = orderStatusRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));

        if (status == null || status.isBlank()) {
            throw new BadRequestException("status is required");
        }
        // 필요 시 허용 상태값 검증 추가
        o.setStatus(status); // 변경감지로 업데이트

        return UpdateOrderStatusResponse.builder()
                .orderId(o.getId())
                .status(o.getStatus())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
