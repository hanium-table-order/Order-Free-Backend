package com.example.tableorder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tableorder.entity.menu.MenuItem;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {

    // 스토어의 모든 메뉴 조회 (MenuItem → Category → Store 관계를 통해)
    List<MenuItem> findByCategoryStoreIdAndSoldOutFalseOrderByCategoryIdAscIdAsc(Long storeId);

    // 특정 카테고리의 메뉴 조회
    List<MenuItem> findByCategoryIdAndCategoryStoreIdAndSoldOutFalseOrderByIdAsc(Long categoryId,Long storeId);
}
