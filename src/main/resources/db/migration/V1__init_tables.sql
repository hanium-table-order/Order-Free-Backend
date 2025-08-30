-- Store 테이블 (가장 먼저 생성되어야 함 - 다른 테이블들이 참조)
CREATE TABLE store (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    business_number VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    address VARCHAR(255),
    hours VARCHAR(255),
    floorplan_url VARCHAR(255)
);

-- StoreTable 테이블
CREATE TABLE store_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    table_number INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    position_x INT,
    position_y INT,
    seats INT,
    CONSTRAINT fk_store_table_store FOREIGN KEY (store_id) REFERENCES store(id) ON DELETE CASCADE
);

-- Category 테이블
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    name_ko VARCHAR(255) NOT NULL,
    name_en VARCHAR(255),
    name_zh VARCHAR(255),
    name_ja VARCHAR(255),
    CONSTRAINT fk_category_store FOREIGN KEY (store_id) REFERENCES store(id) ON DELETE CASCADE
);

-- MenuItem 테이블
CREATE TABLE menu_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    price INT NOT NULL,
    image_url VARCHAR(255),
    sold_out BOOLEAN NOT NULL,
    quantity INT NOT NULL,
    enable_inventory BOOLEAN NOT NULL,
    prep_time_min INT NOT NULL,
    CONSTRAINT fk_menu_item_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- MenuItemI18n 테이블
CREATE TABLE menu_item_i18n (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_item_id BIGINT NOT NULL,
    lang VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000),
    CONSTRAINT fk_menu_item_i18n_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id) ON DELETE CASCADE,
    CONSTRAINT uk_menu_item_lang UNIQUE (menu_item_id, lang)
);

-- MenuOption 테이블
CREATE TABLE menu_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_item_id BIGINT NOT NULL,
    extra_price INT NOT NULL,
    required BOOLEAN NOT NULL,
    CONSTRAINT fk_menu_option_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id) ON DELETE CASCADE
);

-- MenuOptionI18n 테이블
CREATE TABLE menu_option_i18n (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_option_id BIGINT NOT NULL,
    lang VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_menu_option_i18n_menu_option FOREIGN KEY (menu_option_id) REFERENCES menu_option(id) ON DELETE CASCADE,
    CONSTRAINT uk_menu_option_lang UNIQUE (menu_option_id, lang)
);

-- Cart 테이블
CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id BIGINT NOT NULL,
    CONSTRAINT fk_cart_table FOREIGN KEY (table_id) REFERENCES store_table(id) ON DELETE CASCADE
);

-- CartItem 테이블
CREATE TABLE cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    menu_name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id) ON DELETE CASCADE
);

-- Order 테이블
CREATE TABLE `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    total_price INT NOT NULL,
    created_at DATETIME,
    CONSTRAINT fk_order_table FOREIGN KEY (table_id) REFERENCES store_table(id) ON DELETE CASCADE
);

-- OrderItem 테이블
CREATE TABLE order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price INT NOT NULL,
    menu_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id) ON DELETE CASCADE
);

-- OrderItemOption 테이블
CREATE TABLE order_item_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_item_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,
    option_name VARCHAR(255) NOT NULL,
    extra_price INT NOT NULL,
    CONSTRAINT fk_order_item_option_order_item FOREIGN KEY (order_item_id) REFERENCES order_item(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_option_option FOREIGN KEY (option_id) REFERENCES menu_option(id) ON DELETE CASCADE
);

-- Payment 테이블
CREATE TABLE payment (
    transaction_id VARCHAR(255) PRIMARY KEY,
    order_id BIGINT NOT NULL,
    status INT,
    amount INT,
    method VARCHAR(255),
    approved_at DATETIME,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE
);

-- StaffCall 테이블
CREATE TABLE staff_call (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id BIGINT NOT NULL,
    message LONGTEXT,
    requested_at DATETIME,
    CONSTRAINT fk_staff_call_table FOREIGN KEY (table_id) REFERENCES store_table(id) ON DELETE CASCADE
);
