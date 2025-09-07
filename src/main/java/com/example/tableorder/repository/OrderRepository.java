package com.example.tableorder.repository;

import com.example.tableorder.dto.order.LiveOrderDto;
import com.example.tableorder.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        select new com.example.tableorder.dto.order.LiveOrderDto(
            o.id,
            t.id,
            o.status,
            o.createdAt,
            coalesce(sum(oi.unitPrice * oi.quantity), 0L)
        )
        from Order o
        join o.table t
        left join o.orderItems oi
        where t.store.id = :storeId
        group by o.id, t.id, o.status, o.createdAt
        order by o.createdAt desc
    """)
    List<LiveOrderDto> findLiveOrders(@Param("storeId") Long storeId);
}
