package com.example.tableorder.repository;

import com.example.tableorder.entity.menu.MenuOptionI18n;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 옵션 다국어 리포지토리.
 * - 기본 CRUD 제공.
 * - menuOptionId 기반 조회/삭제 메서드 추가.
 */
@Repository
public interface MenuOptionI18nRepository extends JpaRepository<MenuOptionI18n, Long> {

    List<MenuOptionI18n> findByMenuOptionId(Long menuOptionId);

    Optional<MenuOptionI18n> findByMenuOptionIdAndLang(Long menuOptionId, String lang);

    void deleteAllByMenuOptionId(Long menuOptionId);
}