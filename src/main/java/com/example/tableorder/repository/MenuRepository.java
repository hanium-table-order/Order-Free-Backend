package com.example.tableorder.repository;

import com.example.tableorder.entity.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
