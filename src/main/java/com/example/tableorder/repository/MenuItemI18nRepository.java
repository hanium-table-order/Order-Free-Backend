package com.example.tableorder.repository;

import com.example.tableorder.entity.menu.MenuItemI18n;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 아이템 다국어 리포지토리.
 * - 기본 CRUD 제공.
 * - menuItemId 기반 조회/삭제 메서드 추가.
 */
@Repository
public interface MenuItemI18nRepository extends JpaRepository<MenuItemI18n, Long> {

    Optional<MenuItemI18n> findByMenuItemIdAndLang(Long menuItemId, String lang);

    List<MenuItemI18n> findByMenuItemId(Long menuItemId);

    void deleteAllByMenuItemId(Long menuItemId);
}