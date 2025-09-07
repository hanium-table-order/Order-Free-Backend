package com.example.tableorder.repository;

import com.example.tableorder.entity.order.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<Order, Long> {

    @Query("""
        select o
        from Order o
        left join fetch o.table t
        left join fetch o.orderItems oi
        left join fetch oi.menuItem mi
        where o.id = :orderId
    """)
    Optional<Order> findByIdWithItems(@Param("orderId") Long orderId);
}
