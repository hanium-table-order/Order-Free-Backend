-- CartItemOption 테이블 생성
CREATE TABLE cart_item_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_item_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,
    option_name VARCHAR(255) NOT NULL,
    extra_price INT NOT NULL,
    CONSTRAINT fk_cart_item_option_cart_item FOREIGN KEY (cart_item_id) REFERENCES cart_item(id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_option_option FOREIGN KEY (option_id) REFERENCES menu_option(id) ON DELETE CASCADE
);

-- OrderItemOption 테이블 생성 (이미 존재할 수 있음)
CREATE TABLE IF NOT EXISTS order_item_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_item_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,
    option_name VARCHAR(255) NOT NULL,
    extra_price INT NOT NULL,
    CONSTRAINT fk_order_item_option_order_item FOREIGN KEY (order_item_id) REFERENCES order_item(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_option_option FOREIGN KEY (option_id) REFERENCES menu_option(id) ON DELETE CASCADE
);
