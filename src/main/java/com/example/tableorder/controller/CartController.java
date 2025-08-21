package com.example.tableorder.controller;

import com.example.tableorder.dto.CartItemAddRequest;
import com.example.tableorder.dto.CartItemAddResponse;
import com.example.tableorder.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores/{storeId}/tables/{tableId}/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartItemService cartItemService;

    @PostMapping
    public CartItemAddResponse addCartItem(@PathVariable Long storeId,
                                           @PathVariable Long tableId,
                                           @RequestBody CartItemAddRequest request){
        return cartItemService.cartItemCreate(storeId,tableId,request);
    }


}
