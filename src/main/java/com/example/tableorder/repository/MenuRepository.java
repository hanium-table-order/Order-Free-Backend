package com.example.tableorder.repository;

import com.example.tableorder.entity.menu.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuItem, Long> {
}
