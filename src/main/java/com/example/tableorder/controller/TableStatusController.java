package com.example.tableorder.controller;

import com.example.tableorder.dto.table.UpdateTableStatusRequest;
import com.example.tableorder.dto.table.UpdateTableStatusResponse;
import com.example.tableorder.service.TableStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tables")
public class TableStatusController {

    private final TableStatusService service;

    @PatchMapping(path = "/{table_id}/status", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateTableStatusResponse> updateStatus(
            @PathVariable("table_id") Long tableId,
            @RequestBody @Valid UpdateTableStatusRequest request
    ) {
        return ResponseEntity.ok(service.updateStatus(tableId, request.getStatus()));
    }
}
