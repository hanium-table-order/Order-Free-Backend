-- 테이블오더 시스템 - 깔끔한 DB 스키마
-- 핵심 테이블만 포함

-- 1. 가게 테이블
CREATE TABLE store (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    business_number VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    hours VARCHAR(50),
    floorplan_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. 테이블 테이블
CREATE TABLE store_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    table_number INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Empty',
    position_x INT,
    position_y INT,
    seats INT NOT NULL DEFAULT 4,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_store_table_store FOREIGN KEY (store_id) REFERENCES store(id) ON DELETE CASCADE,
    UNIQUE KEY uk_store_table_number (store_id, table_number)
);

-- 3. 카테고리 테이블
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    name_ko VARCHAR(100) NOT NULL,
    name_en VARCHAR(100),
    name_zh VARCHAR(100),
    name_ja VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_category_store FOREIGN KEY (store_id) REFERENCES store(id) ON DELETE CASCADE
);

-- 4. 메뉴 아이템 테이블
CREATE TABLE menu_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    price INT NOT NULL,
    image_url VARCHAR(500),
    sold_out BOOLEAN DEFAULT FALSE,
    quantity INT DEFAULT 0,
    enable_inventory BOOLEAN DEFAULT FALSE,
    prep_time_min INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_menu_item_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- 5. 메뉴 아이템 다국어 테이블
CREATE TABLE menu_item_i18n (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_item_id BIGINT NOT NULL,
    lang VARCHAR(5) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_menu_item_i18n_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id) ON DELETE CASCADE,
    UNIQUE KEY uk_menu_item_i18n (menu_item_id, lang)
);

-- 6. 메뉴 옵션 테이블
CREATE TABLE menu_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_item_id BIGINT NOT NULL,
    extra_price INT NOT NULL DEFAULT 0,
    required BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_menu_option_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id) ON DELETE CASCADE
);

-- 7. 메뉴 옵션 다국어 테이블
CREATE TABLE menu_option_i18n (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_option_id BIGINT NOT NULL,
    lang VARCHAR(5) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_menu_option_i18n_menu_option FOREIGN KEY (menu_option_id) REFERENCES menu_option(id) ON DELETE CASCADE,
    UNIQUE KEY uk_menu_option_i18n (menu_option_id, lang)
);

-- 8. 장바구니 테이블
CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_table FOREIGN KEY (table_id) REFERENCES store_table(id) ON DELETE CASCADE,
    UNIQUE KEY uk_cart_table (table_id)
);

-- 9. 장바구니 아이템 테이블
CREATE TABLE cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price INT NOT NULL,
    menu_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id) ON DELETE CASCADE,
    UNIQUE KEY uk_cart_item_menu (cart_id, menu_item_id)
);

-- 10. 장바구니 아이템 옵션 테이블
CREATE TABLE cart_item_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_item_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,
    option_name VARCHAR(100) NOT NULL,
    extra_price INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_item_option_cart_item FOREIGN KEY (cart_item_id) REFERENCES cart_item(id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_option_option FOREIGN KEY (option_id) REFERENCES menu_option(id) ON DELETE CASCADE
);

-- 11. 주문 테이블
CREATE TABLE `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    total_price INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_table FOREIGN KEY (table_id) REFERENCES store_table(id) ON DELETE CASCADE
);

-- 12. 주문 아이템 테이블
CREATE TABLE order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price INT NOT NULL,
    menu_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id) ON DELETE CASCADE
);

-- 13. 주문 아이템 옵션 테이블
CREATE TABLE order_item_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_item_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,
    option_name VARCHAR(100) NOT NULL,
    extra_price INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_item_option_order_item FOREIGN KEY (order_item_id) REFERENCES order_item(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_option_option FOREIGN KEY (option_id) REFERENCES menu_option(id) ON DELETE CASCADE
);

-- 인덱스 추가 (성능 최적화)
CREATE INDEX idx_store_table_status ON store_table(status);
CREATE INDEX idx_menu_item_category ON menu_item(category_id);
CREATE INDEX idx_cart_item_cart ON cart_item(cart_id);
CREATE INDEX idx_order_table ON `order`(table_id);
CREATE INDEX idx_order_item_order ON order_item(order_id);
