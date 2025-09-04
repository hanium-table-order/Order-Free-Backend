package com.example.tableorder.repository;

import com.example.tableorder.entity.store.StoreTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreTableRepository extends JpaRepository<StoreTable, Long> {

    Optional<StoreTable> findByStore_IdAndId(Long store_Id, Long id);
}
