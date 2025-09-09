package com.example.tableorder.controller;

import com.example.tableorder.dto.order.LiveOrderDto;
import com.example.tableorder.service.OrderLiveDashboardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderLiveDashboardController {

    private final OrderLiveDashboardService service;

    @GetMapping("/live-dashboard")
    public ResponseEntity<List<LiveOrderDto>> getLiveOrders(
            @RequestParam("store_id") Long storeId
    ) {
        return ResponseEntity.ok(service.getLiveOrders(storeId));
    }
}
