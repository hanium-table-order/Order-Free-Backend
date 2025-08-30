package com.example.tableorder.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tableorder.dto.CartItemAddRequest;
import com.example.tableorder.dto.CartItemAddResponse;
import com.example.tableorder.service.CartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stores/{storeId}/tables/{tableId}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping
    public CartItemAddResponse addCartItem(@PathVariable Long storeId,
            @PathVariable Long tableId,
            @RequestBody CartItemAddRequest request) {
        return cartItemService.cartItemCreate(storeId, tableId, request);
    }

}
