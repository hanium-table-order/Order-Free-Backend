package com.example.tableorder.repository;

import com.example.tableorder.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {  // String → Long으로 수정
}