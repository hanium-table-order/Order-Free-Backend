package com.example.tableorder.repository;

import com.example.tableorder.entity.cart.Cart;
import com.example.tableorder.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
}
