package com.example.tableorder.controller;

import com.example.tableorder.dto.floorplan.FloorplanResponse;
import com.example.tableorder.service.FloorplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/floorplan")
public class FloorplanQueryController {

    private final FloorplanService service;

    @GetMapping
    public ResponseEntity<FloorplanResponse> get(
            @RequestParam("store_id") Long storeId
    ) {
        return ResponseEntity.ok(service.getFloorplan(storeId));
    }
}
