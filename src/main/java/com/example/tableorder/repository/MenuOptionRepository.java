package com.example.tableorder.repository;

import com.example.tableorder.entity.menu.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메뉴 옵션 리포지토리.
 * - 기본 CRUD 제공.
 * - menuItemId 기반 조회/삭제 메서드 추가.
 */
@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

    List<MenuOption> findByMenuItemId(Long menuItemId);

    void deleteAllByMenuItemId(Long menuItemId);
}