package com.example.tableorder.repository;

import com.example.tableorder.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 주문 아이템 리포지토리.
 * - 기본 CRUD 제공 (JpaRepository 상속).
 * - 메뉴 ID 기반 존재 여부 쿼리 포함 (메뉴 삭제 FK 제약 체크용).
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * 주어진 메뉴 ID를 참조하는 OrderItem이 존재하는지 확인.
     * - 메뉴 삭제 시 활성 주문 참조 체크용.
     * - 자동 생성 쿼리: SELECT COUNT(*) > 0 FROM order_item WHERE menu_item_id = :menuId
     * @param menuId 메뉴 ID
     * @return 존재 여부 (true: 참조 중, false: 참조 없음)
     */
    boolean existsByMenuItemId(Long menuId);
}