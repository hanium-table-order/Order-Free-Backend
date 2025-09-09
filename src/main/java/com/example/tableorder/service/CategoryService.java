package com.example.tableorder.service;

import com.example.tableorder.dto.CategoryRequestDto;
import com.example.tableorder.dto.CategoryResponseDto;
import com.example.tableorder.entity.category.Category;
import com.example.tableorder.entity.store.Store;
import com.example.tableorder.exception.NotFoundException;
import com.example.tableorder.repository.CategoryRepository;
import com.example.tableorder.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 카테고리 관리 서비스.
 * - 가게 소속 카테고리 추가 처리.
 * - 중복 허용 (동일 이름 가능).
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    /**
     * 카테고리 추가.
     * - storeId 존재 검증.
     * - 다국어 이름 저장.
     * @param storeId 가게 ID
     * @param dto 요청 DTO
     * @return CategoryResponseDto
     */
    @Transactional
    public CategoryResponseDto addCategory(Long storeId, CategoryRequestDto dto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("가게 없음"));

        Category category = Category.builder()
                .store(store)
                .nameKo(dto.getNameKo())
                .nameEn(dto.getNameEn())
                .nameZh(dto.getNameZh())
                .nameJa(dto.getNameJa())
                .build();

        categoryRepository.save(category);
        log.info("카테고리 추가: storeId={}, categoryId={}", storeId, category.getId());

        return toCategoryResponseDto(category);
    }

    private CategoryResponseDto toCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .storeId(category.getStore().getId())
                .nameKo(category.getNameKo())
                .nameEn(category.getNameEn())
                .nameZh(category.getNameZh())
                .nameJa(category.getNameJa())
                .build();
    }
}