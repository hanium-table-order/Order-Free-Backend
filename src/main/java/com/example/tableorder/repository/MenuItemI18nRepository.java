package com.example.tableorder.repository;

import com.example.tableorder.entity.menu.MenuItemI18n;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemI18nRepository extends JpaRepository<MenuItemI18n, Long> {
    Optional<MenuItemI18n> findByMenuItemIdAndLang(Long menuItemId, String lang);
}
