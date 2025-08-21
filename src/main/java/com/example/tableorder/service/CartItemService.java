package com.example.tableorder.service;

import com.example.tableorder.dto.CartItemAddResponse;
import com.example.tableorder.repository.CartItemRepository;
import com.example.tableorder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final MenuRepository menuRepository;

    private CartItemAddResponse mapToCartItemAddResponse(){

    }
}
