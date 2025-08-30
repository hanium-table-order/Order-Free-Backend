package com.example.tableorder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tableorder.dto.CartDetailResponse;
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
    public ResponseEntity<CartItemAddResponse> addCartItem(@PathVariable Long storeId,
            @PathVariable Long tableId,
            @RequestBody CartItemAddRequest request) {
        try {
            CartItemAddResponse response = cartItemService.cartItemCreate(storeId, tableId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<CartDetailResponse> getCartItem(@PathVariable Long storeId,
            @PathVariable Long tableId) {
        try {
            CartDetailResponse response = cartItemService.getCart(storeId, tableId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
