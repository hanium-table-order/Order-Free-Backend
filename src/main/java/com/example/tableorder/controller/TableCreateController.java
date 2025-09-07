package com.example.tableorder.controller;

import com.example.tableorder.dto.table.CreateTableRequest;
import com.example.tableorder.dto.table.CreateTableResponse;
import com.example.tableorder.service.TableCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tables")
public class TableCreateController {

    private final TableCreateService service;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CreateTableResponse> create(
            @RequestBody @Valid CreateTableRequest request
    ) {
        CreateTableResponse res = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
