package com.example.tableorder.service;

import com.example.tableorder.dto.AdminMenuRequestDto;
import com.example.tableorder.dto.AdminMenuResponseDto;
import com.example.tableorder.dto.InventoryUpdateDto;
import com.example.tableorder.entity.menu.MenuItem;
import com.example.tableorder.entity.menu.MenuItemI18n;
import com.example.tableorder.entity.menu.MenuOption;
import com.example.tableorder.entity.menu.MenuOptionI18n;
import com.example.tableorder.exception.BusinessRuleException;
import com.example.tableorder.exception.ConflictException;
import com.example.tableorder.exception.NotFoundException;
import com.example.tableorder.repository.AdminMenuRepository;
import com.example.tableorder.repository.CategoryRepository;
import com.example.tableorder.repository.MenuItemI18nRepository;
import com.example.tableorder.repository.MenuOptionI18nRepository;
import com.example.tableorder.repository.MenuOptionRepository;
import com.example.tableorder.repository.StoreRepository;
import com.example.tableorder.util.EventBroadcaster;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 점주 파트 메뉴 관리 서비스.
 * - 메뉴 등록/수정/삭제, 재고 업데이트 처리.
 * - 고객 파트와 독립적으로 동작.
 * - 검증, 트랜잭션, 로깅, 브로드캐스트 지원.
 */
@Service
@RequiredArgsConstructor
public class AdminMenuService {

    private static final Logger log = LoggerFactory.getLogger(AdminMenuService.class);

    private final AdminMenuRepository adminMenuRepository;
    private final CategoryRepository categoryRepository;
    private final MenuItemI18nRepository menuItemI18nRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuOptionI18nRepository menuOptionI18nRepository;
    private final StoreRepository storeRepository;
    private final EventBroadcaster eventBroadcaster;  // 브로드캐스트 스텁

    /**
     * 메뉴 등록.
     * - 가게/카테고리 소속 검증.
     * - menuId 제공 시 중복 체크.
     * - i18n/options 저장.
     * - 브로드캐스트 호출.
     * @param storeId 가게 ID
     * @param menuId 클라이언트 제공 ID (0 또는 미제공 시 자동 발번)
     * @param dto 요청 DTO
     * @return AdminMenuResponseDto
     */
    @Transactional
    public AdminMenuResponseDto createMenu(Long storeId, Long menuId, @Valid AdminMenuRequestDto dto) {
        validateStoreExists(storeId);
        validateCategoryBelongsToStore(dto.getCategoryId(), storeId);

        if (menuId != null && menuId > 0) {
            if (adminMenuRepository.existsById(menuId)) {
                throw new ConflictException("메뉴 ID 중복");
            }
        } else {
            menuId = null;  // 자동 발번
        }

        MenuItem menuItem = MenuItem.builder()
                .id(menuId)
                .category(categoryRepository.getReferenceById(dto.getCategoryId()))
                .price(dto.getPrice() != null ? dto.getPrice() : 0)
                .imageUrl(dto.getImage())
                .soldOut(dto.getSoldOut() != null ? dto.getSoldOut() : false)
                .quantity(dto.getQuantity() != null ? dto.getQuantity() : 0)
                .enableInventory(dto.getEnableInventory() != null ? dto.getEnableInventory() : false)
                .prepTimeMin(0)  // DTO에 없으므로 default
                .build();

        // i18n 저장
        List<MenuItemI18n> i18ns = new ArrayList<>();
        for (String lang : dto.getName().keySet()) {
            String name = dto.getName().get(lang);
            String description = dto.getDescription().get(lang);
            i18ns.add(MenuItemI18n.builder()
                    .menuItem(menuItem)
                    .lang(lang)
                    .name(name)
                    .description(description)
                    .build());
        }
        validateI18nRequiredLanguages(i18ns);
        menuItemI18nRepository.saveAll(i18ns);

        // options 저장
        if (dto.getOptions() != null) {
            for (AdminMenuRequestDto.OptionDto opt : dto.getOptions()) {
                MenuOption option = MenuOption.builder()
                        .menuItem(menuItem)
                        .extraPrice(opt.getExtraPrice() != null ? opt.getExtraPrice() : 0)
                        .required(opt.getRequired())
                        .build();
                menuOptionRepository.save(option);

                List<MenuOptionI18n> optI18ns = new ArrayList<>();
                for (String lang : opt.getName().keySet()) {
                    String name = opt.getName().get(lang);
                    String description = opt.getDescription().get(lang);
                    optI18ns.add(MenuOptionI18n.builder()
                            .menuOption(option)
                            .lang(lang)
                            .name(name)
                            .description(description)
                            .build());
                }
                menuOptionI18nRepository.saveAll(optI18ns);
            }
        }

        adminMenuRepository.save(menuItem);
        log.info("메뉴 등록: storeId={}, menuId={}", storeId, menuItem.getId());

        eventBroadcaster.publish("ws.inventory." + storeId + ".changed");

        return toAdminMenuResponseDto(menuItem);
    }

    /**
     * 메뉴 부분 수정.
     * - 부분 업데이트 지원 (null 무시).
     * - 재고 변경 시 원자 업데이트.
     * - 브로드캐스트 호출.
     * @param storeId 가게 ID
     * @param menuId 메뉴 ID
     * @param dto 요청 DTO
     * @return AdminMenuResponseDto
     */
    @Transactional
    public AdminMenuResponseDto patchMenu(Long storeId, Long menuId, @Valid AdminMenuRequestDto dto) {
        MenuItem menuItem = getMenuItemOrThrow(menuId);
        validateMenuBelongsToStore(menuItem, storeId);

        if (dto.getCategoryId() != null) {
            validateCategoryBelongsToStore(dto.getCategoryId(), storeId);
            menuItem.setCategory(categoryRepository.getReferenceById(dto.getCategoryId()));
        }
        if (dto.getPrice() != null) menuItem.setPrice(dto.getPrice());
        if (dto.getImage() != null) menuItem.setImageUrl(dto.getImage());
        if (dto.getSoldOut() != null) menuItem.setSoldOut(dto.getSoldOut());
        if (dto.getEnableInventory() != null) menuItem.setEnableInventory(dto.getEnableInventory());
        // prepTimeMin DTO에 없음, 무시

        if (dto.getQuantity() != null) {
            int delta = dto.getQuantity() - menuItem.getQuantity();
            int rowsUpdated = adminMenuRepository.updateQuantityAtomically(menuId, delta);
            if (rowsUpdated == 0) {
                throw new ConflictException("재고 업데이트 실패: 재고 부족 또는 동시성 충돌");
            }
            menuItem.setQuantity(dto.getQuantity());
        }

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            menuItemI18nRepository.deleteAllByMenuItemId(menuId);
            List<MenuItemI18n> newI18ns = new ArrayList<>();
            for (String lang : dto.getName().keySet()) {
                String name = dto.getName().get(lang);
                String description = dto.getDescription().get(lang);
                newI18ns.add(MenuItemI18n.builder()
                        .menuItem(menuItem)
                        .lang(lang)
                        .name(name)
                        .description(description)
                        .build());
            }
            validateI18nRequiredLanguages(newI18ns);
            menuItemI18nRepository.saveAll(newI18ns);
        }

        if (dto.getOptions() != null) {
            // options i18n 삭제를 위해 루프
            menuOptionRepository.findByMenuItemId(menuId).forEach(opt -> menuOptionI18nRepository.deleteAllByMenuOptionId(opt.getId()));
            menuOptionRepository.deleteAllByMenuItemId(menuId);
            for (AdminMenuRequestDto.OptionDto opt : dto.getOptions()) {
                MenuOption option = MenuOption.builder()
                        .menuItem(menuItem)
                        .extraPrice(opt.getExtraPrice() != null ? opt.getExtraPrice() : 0)
                        .required(opt.getRequired())
                        .build();
                menuOptionRepository.save(option);

                List<MenuOptionI18n> optI18ns = new ArrayList<>();
                for (String lang : opt.getName().keySet()) {
                    String name = opt.getName().get(lang);
                    String description = opt.getDescription().get(lang);
                    optI18ns.add(MenuOptionI18n.builder()
                            .menuOption(option)
                            .lang(lang)
                            .name(name)
                            .description(description)
                            .build());
                }
                menuOptionI18nRepository.saveAll(optI18ns);
            }
        }

        adminMenuRepository.save(menuItem);
        log.info("메뉴 수정: storeId={}, menuId={}", storeId, menuId);

        eventBroadcaster.publish("ws.inventory." + storeId + ".changed");

        return toAdminMenuResponseDto(menuItem);
    }

    /**
     * 메뉴 삭제.
     * - FK 제약 확인.
     * - 브로드캐스트 호출.
     * @param storeId 가게 ID
     * @param menuId 메뉴 ID
     * @return 삭제 메시지
     */
    @Transactional
    public String deleteMenu(Long storeId, Long menuId) {
        MenuItem menuItem = getMenuItemOrThrow(menuId);
        validateMenuBelongsToStore(menuItem, storeId);

        if (hasActiveOrders(menuId)) {
            throw new ConflictException("삭제 불가: 활성 주문 참조 중");
        }

        adminMenuRepository.delete(menuItem);
        log.info("메뉴 삭제: storeId={}, menuId={}", storeId, menuId);

        eventBroadcaster.publish("ws.inventory." + storeId + ".changed");

        return "삭제 완료";
    }

    /**
     * 재고 업데이트 (InventoryUpdateDto 사용).
     * - 원자 업데이트 호출.
     * - 브로드캐스트 호출.
     * @param storeId 가게 ID
     * @param menuId 메뉴 ID
     * @param dto 업데이트 DTO
     * @return AdminMenuResponseDto
     */
    @Transactional
    public AdminMenuResponseDto updateInventory(Long storeId, Long menuId, @Valid InventoryUpdateDto dto) {
        MenuItem menuItem = getMenuItemOrThrow(menuId);
        validateMenuBelongsToStore(menuItem, storeId);

        int delta = dto.getDelta() != null ? dto.getDelta() : 0;  // delta 직접 사용
        int rowsUpdated = adminMenuRepository.updateQuantityAtomically(menuId, delta);
        if (rowsUpdated == 0) {
            throw new ConflictException("재고 업데이트 실패: 재고 부족 또는 동시성 충돌");
        }

        if (dto.getEnableInventory() != null) menuItem.setEnableInventory(dto.getEnableInventory());
        menuItem.setQuantity(menuItem.getQuantity() + delta);  // reload 없이 반영 (트랜잭션 내)

        adminMenuRepository.save(menuItem);
        log.info("재고 업데이트: storeId={}, menuId={}, delta={}", storeId, menuId, delta);

        eventBroadcaster.publish("ws.inventory." + storeId + ".changed");

        return toAdminMenuResponseDto(menuItem);
    }

    // 헬퍼 메서드들 (private)
    private MenuItem getMenuItemOrThrow(Long menuId) {
        return adminMenuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("메뉴 없음"));
    }

    private void validateStoreExists(Long storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new NotFoundException("가게 없음");
        }
    }

    private void validateCategoryBelongsToStore(Long categoryId, Long storeId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(
                cat -> {
                    if (!cat.getStore().getId().equals(storeId)) {
                        throw new BusinessRuleException("카테고리 소속 불일치");
                    }
                },
                () -> { throw new NotFoundException("카테고리 없음"); }
        );
    }

    private void validateMenuBelongsToStore(MenuItem menuItem, Long storeId) {
        if (!menuItem.getCategory().getStore().getId().equals(storeId)) {
            throw new BusinessRuleException("메뉴 소속 불일치");
        }
    }

    private void validateI18nRequiredLanguages(List<MenuItemI18n> i18ns) {
        List<String> langs = i18ns.stream().map(MenuItemI18n::getLang).collect(Collectors.toList());
        if (!langs.containsAll(List.of("ko", "en", "zh", "ja"))) {
            throw new BusinessRuleException("i18n 필수 언어 누락: ko/en/zh/ja");
        }
    }

    private boolean hasActiveOrders(Long menuId) {
        // OrderItemRepository 없으므로 스텁
        return false;
    }

    private AdminMenuResponseDto toAdminMenuResponseDto(MenuItem menuItem) {
        Map<String, String> nameMap = menuItemI18nRepository.findByMenuItemId(menuItem.getId()).stream()
                .collect(Collectors.toMap(MenuItemI18n::getLang, MenuItemI18n::getName));
        Map<String, String> descriptionMap = menuItemI18nRepository.findByMenuItemId(menuItem.getId()).stream()
                .collect(Collectors.toMap(MenuItemI18n::getLang, MenuItemI18n::getDescription));

        List<AdminMenuResponseDto.OptionDto> options = menuOptionRepository.findByMenuItemId(menuItem.getId()).stream()
                .map(opt -> {
                    Map<String, String> optNameMap = menuOptionI18nRepository.findByMenuOptionId(opt.getId()).stream()
                            .collect(Collectors.toMap(MenuOptionI18n::getLang, MenuOptionI18n::getName));
                    Map<String, String> optDescriptionMap = menuOptionI18nRepository.findByMenuOptionId(opt.getId()).stream()
                            .collect(Collectors.toMap(MenuOptionI18n::getLang, MenuOptionI18n::getDescription));
                    return AdminMenuResponseDto.OptionDto.builder()
                            .id(String.valueOf(opt.getId()))
                            .extraPrice(opt.getExtraPrice())
                            .required(opt.getRequired())
                            .name(optNameMap)
                            .description(optDescriptionMap)
                            .build();
                })
                .collect(Collectors.toList());

        return AdminMenuResponseDto.builder()
                .menuId(String.valueOf(menuItem.getId()))
                .categoryId(String.valueOf(menuItem.getCategory().getId()))
                .name(nameMap)
                .price(menuItem.getPrice())
                .description(descriptionMap)
                .options(options)
                .image(menuItem.getImageUrl())
                .soldOut(menuItem.getSoldOut())
                .quantity(menuItem.getQuantity())
                .enableInventory(menuItem.getEnableInventory())
                .message(null)
                .build();
    }
}