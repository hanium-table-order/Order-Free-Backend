-- V4__add_order_data.sql (수정된 버전)

-- 변수 선언
SET @store_id = 1;
SET @table_id = (SELECT id FROM store_table WHERE store_id = @store_id AND table_number = 1 LIMIT 1);
SET @kimchi_stew_id = (SELECT id FROM menu_item WHERE category_id IN (SELECT id FROM category WHERE store_id = @store_id) AND price = 15000 LIMIT 1); -- 김치찌개
SET @bulgogi_id = (SELECT id FROM menu_item WHERE category_id IN (SELECT id FROM category WHERE store_id = @store_id) AND price = 18000 LIMIT 1); -- 불고기
SET @rice_option_id = (SELECT mo.id FROM menu_option mo JOIN menu_item mi ON mo.menu_item_id = mi.id WHERE mi.id = @kimchi_stew_id AND mo.extra_price = 2000 LIMIT 1); -- 김치찌개에 속한 '밥 추가' 옵션

-- 주문1: 김치찌개 2개와 밥 추가 옵션 1개
INSERT INTO `order` (table_id, status, total_price) VALUES (@table_id, 'PREPARING', 32000);
SET @order1_id = LAST_INSERT_ID();

INSERT INTO order_item (order_id, menu_item_id, quantity, unit_price, menu_name) VALUES (@order1_id, @kimchi_stew_id, 2, 15000, '김치찌개');
SET @order1_item_id = LAST_INSERT_ID();

INSERT INTO order_item_option (order_item_id, option_id, option_name, extra_price, quantity) VALUES (@order1_item_id, @rice_option_id, '밥 추가', 2000, 1);

-- 주문2: 불고기 1개
INSERT INTO `order` (table_id, status, total_price) VALUES (@table_id, 'SERVED', 18000);
SET @order2_id = LAST_INSERT_ID();

INSERT INTO order_item (order_id, menu_item_id, quantity, unit_price, menu_name) VALUES (@order2_id, @bulgogi_id, 1, 18000, '불고기');