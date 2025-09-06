package com.example.tableorder.controller;

import com.example.tableorder.dto.table.TableListItemDto;
import com.example.tableorder.service.TableQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tables")
public class TableQueryController {

    private final TableQueryService service;

    @GetMapping
    public ResponseEntity<List<TableListItemDto>> list(
            @RequestParam("store_id") Long storeId
    ) {
        return ResponseEntity.ok(service.listTables(storeId));
    }
}
