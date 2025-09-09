package com.example.tableorder.repository;

import com.example.tableorder.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<Order, Long> { }
