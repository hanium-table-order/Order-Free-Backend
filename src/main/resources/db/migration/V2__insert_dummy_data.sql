-- 더미 데이터 삽입

-- Store 더미 데이터
INSERT INTO store (business_number, name, address, hours, floorplan_url) VALUES 
('123-45-67890', '맛있는 한식당', '서울시 강남구 테헤란로 123', '09:00-22:00', 'https://example.com/floorplan1.jpg'),
('987-65-43210', '좋은 카페', '서울시 서초구 서초대로 456', '07:00-23:00', 'https://example.com/floorplan2.jpg'),
('555-44-33333', '피자 전문점', '서울시 마포구 홍대로 789', '11:00-24:00', 'https://example.com/floorplan3.jpg');

-- StoreTable 더미 데이터
INSERT INTO store_table (store_id, table_number, status, position_x, position_y, seats) VALUES 
(1, 1, 'Empty', 100, 100, 4),
(1, 2, 'Empty', 200, 100, 4),
(1, 3, 'Empty', 300, 100, 6),
(1, 4, 'Empty', 100, 200, 2),
(2, 1, 'Empty', 150, 150, 4),
(2, 2, 'Empty', 250, 150, 4),
(3, 1, 'Empty', 120, 120, 4),
(3, 2, 'Empty', 220, 120, 6);

-- Category 더미 데이터
INSERT INTO category (store_id, name_ko, name_en, name_zh, name_ja) VALUES 
(1, '메인 요리', 'Main Dishes', '主菜', 'メインディッシュ'),
(1, '사이드 메뉴', 'Side Dishes', '配菜', 'サイドメニュー'),
(1, '음료', 'Beverages', '飲料', 'ドリンク'),
(2, '커피', 'Coffee', '咖啡', 'コーヒー'),
(2, '논커피', 'Non-Coffee', '非咖啡', 'ノンコーヒー'),
(2, '디저트', 'Dessert', '甜点', 'デザート'),
(3, '피자', 'Pizza', '披萨', 'ピザ'),
(3, '파스타', 'Pasta', '意大利面', 'パスタ'),
(3, '샐러드', 'Salad', '沙拉', 'サラダ');

-- MenuItem 더미 데이터
INSERT INTO menu_item (category_id, price, image_url, sold_out, quantity, enable_inventory, prep_time_min) VALUES 
(1, 15000, 'https://example.com/kimchi.jpg', false, 100, true, 15),
(1, 12000, 'https://example.com/doenjang.jpg', false, 80, true, 12),
(1, 18000, 'https://example.com/bulgogi.jpg', false, 50, true, 20),
(2, 5000, 'https://example.com/rice.jpg', false, 200, true, 5),
(2, 3000, 'https://example.com/kimchi-side.jpg', false, 150, true, 3),
(4, 4500, 'https://example.com/americano.jpg', false, 100, true, 3),
(4, 5500, 'https://example.com/latte.jpg', false, 80, true, 5),
(5, 6000, 'https://example.com/ade.jpg', false, 60, true, 4),
(6, 7000, 'https://example.com/cake.jpg', false, 30, true, 2),
(7, 25000, 'https://example.com/pizza.jpg', false, 40, true, 25),
(8, 18000, 'https://example.com/pasta.jpg', false, 35, true, 18),
(9, 12000, 'https://example.com/salad.jpg', false, 25, true, 8);

-- MenuItemI18n 더미 데이터 (한국어)
INSERT INTO menu_item_i18n (menu_item_id, lang, name, description) VALUES 
(1, 'ko', '김치찌개', '매콤한 김치와 돼지고기로 만든 찌개'),
(2, 'ko', '된장찌개', '구수한 된장으로 만든 찌개'),
(3, 'ko', '불고기', '달콤한 양념에 구운 돼지고기'),
(4, 'ko', '공기밥', '쌀밥 한 공기'),
(5, 'ko', '김치', '매콤한 김치'),
(6, 'ko', '아메리카노', '깔끔한 아메리카노'),
(7, 'ko', '카페라떼', '부드러운 카페라떼'),
(8, 'ko', '레몬에이드', '상큼한 레몬에이드'),
(9, 'ko', '티라미수', '부드러운 티라미수'),
(10, 'ko', '페퍼로니 피자', '매콤한 페퍼로니 피자'),
(11, 'ko', '까르보나라', '계란 노른자와 파마산 치즈 파스타'),
(12, 'ko', '시저샐러드', '신선한 채소와 드레싱');

-- MenuItemI18n 더미 데이터 (영어)
INSERT INTO menu_item_i18n (menu_item_id, lang, name, description) VALUES 
(1, 'en', 'Kimchi Stew', 'Spicy stew made with kimchi and pork'),
(2, 'en', 'Doenjang Stew', 'Stew made with traditional soybean paste'),
(3, 'en', 'Bulgogi', 'Sweet marinated grilled pork'),
(4, 'en', 'Steamed Rice', 'A bowl of steamed rice'),
(5, 'en', 'Kimchi', 'Spicy fermented cabbage'),
(6, 'en', 'Americano', 'Clean Americano coffee'),
(7, 'en', 'Cafe Latte', 'Smooth cafe latte'),
(8, 'en', 'Lemonade', 'Refreshing lemonade'),
(9, 'en', 'Tiramisu', 'Smooth tiramisu'),
(10, 'en', 'Pepperoni Pizza', 'Spicy pepperoni pizza'),
(11, 'en', 'Carbonara', 'Pasta with egg yolk and parmesan cheese'),
(12, 'en', 'Caesar Salad', 'Fresh vegetables with dressing');

-- MenuOption 더미 데이터
INSERT INTO menu_option (menu_item_id, extra_price, required) VALUES 
(1, 2000, false),  -- 김치찌개 - 밥 추가
(1, 1000, false),  -- 김치찌개 - 김치 추가
(6, 500, false),   -- 아메리카노 - 샷 추가
(7, 500, false),   -- 카페라떼 - 샷 추가
(10, 3000, false), -- 피자 - 치즈 추가
(10, 2000, false); -- 피자 - 토핑 추가

-- MenuOptionI18n 더미 데이터
INSERT INTO menu_option_i18n (menu_option_id, lang, name, description) VALUES 
(1, 'ko', '밥 추가', '쌀밥 한 공기 추가'),
(1, 'en', 'Extra Rice', 'Add a bowl of steamed rice'),
(2, 'ko', '김치 추가', '매콤한 김치 한 접시 추가'),
(2, 'en', 'Extra Kimchi', 'Add a plate of spicy kimchi'),
(3, 'ko', '샷 추가', '에스프레소 샷 하나 추가'),
(3, 'en', 'Extra Shot', 'Add one espresso shot'),
(4, 'ko', '샷 추가', '에스프레소 샷 하나 추가'),
(4, 'en', 'Extra Shot', 'Add one espresso shot'),
(5, 'ko', '치즈 추가', '모짜렐라 치즈 추가'),
(5, 'en', 'Extra Cheese', 'Add mozzarella cheese'),
(6, 'ko', '토핑 추가', '다양한 토핑 추가'),
(6, 'en', 'Extra Topping', 'Add various toppings');
