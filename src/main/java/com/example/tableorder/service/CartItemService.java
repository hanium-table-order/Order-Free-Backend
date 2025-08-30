package com.example.tableorder.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tableorder.dto.CartDetailResponse;
import com.example.tableorder.dto.CartItemAddRequest;
import com.example.tableorder.dto.CartItemAddResponse;
import com.example.tableorder.entity.cart.Cart;
import com.example.tableorder.entity.cart.CartItem;
import com.example.tableorder.entity.menu.MenuItem;
import com.example.tableorder.entity.menu.MenuItemI18n;
import com.example.tableorder.entity.store.StoreTable;
import com.example.tableorder.repository.CartItemRepository;
import com.example.tableorder.repository.CartRepository;
import com.example.tableorder.repository.MenuItemI18nRepository;
import com.example.tableorder.repository.MenuRepository;
import com.example.tableorder.repository.StoreTableRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final MenuRepository menuRepository;
    private final MenuItemI18nRepository menuItemI18nRepository;
    private final CartRepository cartRepository;
    private final StoreTableRepository storeTableRepository;

    private CartItemAddResponse mapToCartItemAddResponse(CartItem cartItem) {
        int q = cartItem.getQuantity();
        int p = cartItem.getPrice();
        return CartItemAddResponse.builder()
                .cartItemId(cartItem.getId())
                .menuItemId(cartItem.getMenuItem().getId())
                .menuName(cartItem.getMenuName())
                .quantity(q)
                .price(p)
                .linePrice(p * q)
                .build();
    }

    @Transactional
    public CartItemAddResponse cartItemCreate(Long storeId, Long tableId, CartItemAddRequest request) {

        // 입력검증
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("quantity는 1 이상이어야 합니다.");
        }
        String lang = (request.getLang() == null || request.getLang().isBlank()) ? "ko" : request.getLang();

        // StoreTable 조회
        StoreTable storeTable = storeTableRepository.findByStoreIdAndId(storeId, tableId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 테이블"));

        // 장바구니 조회, 없으면 생성
        Cart cart = cartRepository.findByTable(storeTable)
                .orElseGet(() -> cartRepository.save(
                Cart.builder().table(storeTable).build()
        ));

        // 메뉴 조회
        MenuItem menuItem = menuRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴 ID"));
        // 메뉴 가격 가져오기
        int price = menuItem.getPrice();

        // 언어 기준으로 이름 조회
        String name = menuItemI18nRepository.findByMenuItemIdAndLang(menuItem.getId(), request.getLang())
                .map(MenuItemI18n::getName)
                .orElse("이름없음");

        // 장바구니
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .menuItem(menuItem)
                .quantity(request.getQuantity())
                .price(price)
                .menuName(name)
                .build();

        CartItem saved = cartItemRepository.save(cartItem);
        return mapToCartItemAddResponse(saved);
    }

    private CartDetailResponse mapToCartDetailResponse(List<CartItem> cartItems) {

        List<CartDetailResponse.Item> items = cartItems.stream()
                .map(item -> CartDetailResponse.Item.builder()
                .cartItemId(item.getId())
                .menuItemId(item.getMenuItem().getId())
                .menuName(item.getMenuName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .linePrice(item.getQuantity() * item.getPrice())
                .build())
                .toList();

        int total = items.stream()
                .mapToInt(CartDetailResponse.Item::getLinePrice)
                .sum();

        return CartDetailResponse.builder()
                .items(items)
                .cartTotalPrice(total)
                .build();
    }

    @Transactional
    public CartDetailResponse getCart(Long storeId, Long tableId) {
        StoreTable storeTable = storeTableRepository.findByStoreIdAndId(storeId, tableId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 테이블"));

        Cart cart = cartRepository.findByTable(storeTable)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어있습니다.");
        }

        return mapToCartDetailResponse(cartItems);
    }
}
