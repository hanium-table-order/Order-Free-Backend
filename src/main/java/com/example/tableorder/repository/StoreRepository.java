package com.example.tableorder.repository;

import com.example.tableorder.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 가게 리포지토리.
 * - 기본 CRUD 제공.
 * - businessNumber 유니크 검증을 위한 findByBusinessNumber 추가.
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * 사업자 번호로 가게 조회 (중복 검증용).
     * @param businessNumber 사업자 번호
     * @return Optional<Store>
     */
    Optional<Store> findByBusinessNumber(String businessNumber);
}