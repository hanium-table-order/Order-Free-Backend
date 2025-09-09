package com.example.tableorder.repository;

import com.example.tableorder.entity.menu.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 점주 파트 전용 메뉴 아이템 리포지토리.
 * - 기본 CRUD 제공 (JpaRepository 상속).
 * - 재고 원자 업데이트 쿼리 포함 (동시성 제어, 점주 API 전용).
 */
@Repository
public interface AdminMenuRepository extends JpaRepository<MenuItem, Long> {

    /**
     * 재고 수량 원자 업데이트 (점주 재고 관리 API용).
     * - enable_inventory=true 시 delta 적용 후 음수 방지.
     * - sold_out 자동 업데이트 (quantity + delta <= 0 시 true).
     * - 업데이트 행 수 반환 (0이면 실패: 재고 부족 또는 동시성 충돌).
     * @param menuId 메뉴 ID
     * @param delta 수량 변화량 (양수/음수 허용)
     * @return 업데이트 된 행 수
     */
    @Modifying
    @Query(value = """
        UPDATE menu_item
        SET quantity = quantity + :delta,
            sold_out = (CASE WHEN (quantity + :delta) <= 0 THEN TRUE ELSE sold_out END)
        WHERE id = :menuId
        AND (enable_inventory = FALSE OR (enable_inventory = TRUE AND (quantity + :delta) >= 0))
    """, nativeQuery = true)
    int updateQuantityAtomically(@Param("menuId") Long menuId, @Param("delta") int delta);
}