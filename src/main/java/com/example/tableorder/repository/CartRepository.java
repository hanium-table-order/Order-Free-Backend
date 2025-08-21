package com.example.tableorder.repository;

import com.example.tableorder.entity.cart.Cart;
import com.example.tableorder.entity.store.StoreTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByTable(StoreTable storeTable);
}
