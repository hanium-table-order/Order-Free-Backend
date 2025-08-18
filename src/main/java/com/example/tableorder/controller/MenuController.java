package com.example.tableorder.controller;

import com.example.tableorder.entity.menu.MenuItem;
import com.example.tableorder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;

    @GetMapping("/menus")
    public List<MenuItem> getMenus() {
        // 그냥 전부 조회 — JSON 컬럼은 문자열로 내려간다
        return menuRepository.findAll();
    }
}
