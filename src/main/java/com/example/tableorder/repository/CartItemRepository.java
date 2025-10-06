package com.example.tableorder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tableorder.entity.cart.Cart;
import com.example.tableorder.entity.cart.CartItem;
import com.example.tableorder.entity.menu.MenuItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndMenuItem(Cart cart, MenuItem menuItem);

    Optional<CartItem> findByIdAndCartId(Long cartItemId,Long cartId);
}
