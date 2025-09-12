package com.example.tableorder.repository;

import com.example.tableorder.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 점주용 주문 리포지토리.
 * - storeId 기준 주문 조회.
 */
@Repository
public interface AdminOrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN o.table t JOIN t.store s WHERE s.id = :storeId ORDER BY o.createdAt DESC")
    List<Order> findByStoreId(Long storeId);
}