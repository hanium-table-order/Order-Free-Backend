package com.example.tableorder.repository;

import com.example.tableorder.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 아이템 리포지토리.
 * - 기본 CRUD 제공 (주문 아이템 저장/조회).
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 최소 구현: 추가 쿼리 없이 saveAll로 충분
}