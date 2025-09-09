-- 테이블오더 시스템 - 깔끔한 더미 데이터
-- 핵심 테스트 데이터만 포함

-- 1. 가게 데이터
INSERT INTO store (business_number, name, address, hours, floorplan_url) VALUES 
('123-45-67890', '맛있는 한식당', '서울시 강남구 테헤란로 123', '09:00-22:00', 'https://example.com/floorplan1.jpg'),
('987-65-43210', '좋은 카페', '서울시 서초구 서초대로 456', '07:00-23:00', 'https://example.com/floorplan2.jpg'),
('555-44-33333', '피자 전문점', '서울시 마포구 홍대로 789', '11:00-24:00', 'https://example.com/floorplan3.jpg');

-- 2. 테이블 데이터
INSERT INTO store_table (store_id, table_number, status, position_x, position_y, seats) VALUES 
-- 한식당 테이블
(1, 1, 'Empty', 100, 100, 4),
(1, 2, 'Empty', 200, 100, 4),
(1, 3, 'Empty', 300, 100, 6),
-- 카페 테이블
(2, 1, 'Empty', 150, 150, 2),
(2, 2, 'Empty', 250, 150, 4),
-- 피자 전문점 테이블
(3, 1, 'Empty', 120, 120, 4),
(3, 2, 'Empty', 220, 120, 6);

-- 3. 카테고리 데이터
INSERT INTO category (store_id, name_ko, name_en, name_zh, name_ja) VALUES 
-- 한식당 카테고리
(1, '메인 요리', 'Main Dishes', '主菜', 'メインディッシュ'),
(1, '사이드 메뉴', 'Side Dishes', '配菜', 'サイドメニュー'),
(1, '음료', 'Beverages', '飲料', 'ドリンク'),
-- 카페 카테고리
(2, '커피', 'Coffee', '咖啡', 'コーヒー'),
(2, '논커피', 'Non-Coffee', '非咖啡', 'ノンコーヒー'),
(2, '디저트', 'Dessert', '甜点', 'デザート'),
-- 피자 전문점 카테고리
(3, '피자', 'Pizza', '披萨', 'ピザ'),
(3, '파스타', 'Pasta', '意大利面', 'パスタ'),
(3, '샐러드', 'Salad', '沙拉', 'サラダ');

-- 4. 메뉴 아이템 데이터
INSERT INTO menu_item (category_id, price, image_url, sold_out, quantity, enable_inventory, prep_time_min) VALUES 
-- 한식당 - 메인 요리
(1, 15000, 'https://example.com/kimchi.jpg', false, 100, true, 15),  -- 김치찌개
(1, 12000, 'https://example.com/doenjang.jpg', false, 80, true, 12), -- 된장찌개
(1, 18000, 'https://example.com/bulgogi.jpg', false, 50, true, 20),  -- 불고기
-- 한식당 - 사이드 메뉴
(2, 5000, 'https://example.com/rice.jpg', false, 200, true, 5),      -- 공기밥
(2, 3000, 'https://example.com/kimchi-side.jpg', false, 150, true, 3), -- 김치
-- 한식당 - 음료
(3, 4500, 'https://example.com/americano.jpg', false, 100, true, 3), -- 아메리카노
(3, 5500, 'https://example.com/latte.jpg', false, 80, true, 5),      -- 카페라떼
-- 카페 - 커피
(4, 4500, 'https://example.com/americano.jpg', false, 100, true, 3), -- 아메리카노
(4, 5500, 'https://example.com/latte.jpg', false, 80, true, 5),      -- 카페라떼
(4, 6000, 'https://example.com/cappuccino.jpg', false, 60, true, 4), -- 카푸치노
-- 카페 - 논커피
(5, 6000, 'https://example.com/ade.jpg', false, 60, true, 4),        -- 레몬에이드
(5, 5000, 'https://example.com/tea.jpg', false, 80, true, 3),        -- 허브티
-- 카페 - 디저트
(6, 7000, 'https://example.com/cake.jpg', false, 30, true, 2),       -- 케이크
(6, 8000, 'https://example.com/tiramisu.jpg', false, 25, true, 3),   -- 티라미수
-- 피자 전문점 - 피자
(7, 25000, 'https://example.com/pizza.jpg', false, 40, true, 25),    -- 페퍼로니 피자
(7, 28000, 'https://example.com/margherita.jpg', false, 35, true, 20), -- 마르게리타 피자
-- 피자 전문점 - 파스타
(8, 18000, 'https://example.com/pasta.jpg', false, 35, true, 18),    -- 까르보나라
(8, 16000, 'https://example.com/aglio.jpg', false, 40, true, 15),    -- 알리오 올리오
-- 피자 전문점 - 샐러드
(9, 12000, 'https://example.com/salad.jpg', false, 25, true, 8);     -- 시저샐러드

-- 5. 메뉴 아이템 다국어 데이터 (한국어)
INSERT INTO menu_item_i18n (menu_item_id, lang, name, description) VALUES 
-- 한식당
(1, 'ko', '김치찌개', '매콤한 김치와 돼지고기로 만든 찌개'),
(2, 'ko', '된장찌개', '구수한 된장으로 만든 찌개'),
(3, 'ko', '불고기', '달콤한 양념에 구운 돼지고기'),
(4, 'ko', '공기밥', '쌀밥 한 공기'),
(5, 'ko', '김치', '매콤한 김치'),
(6, 'ko', '아메리카노', '깔끔한 아메리카노'),
(7, 'ko', '카페라떼', '부드러운 카페라떼'),
-- 카페
(8, 'ko', '아메리카노', '깔끔한 아메리카노'),
(9, 'ko', '카페라떼', '부드러운 카페라떼'),
(10, 'ko', '카푸치노', '진한 에스프레소와 우유 거품'),
(11, 'ko', '레몬에이드', '상큼한 레몬에이드'),
(12, 'ko', '허브티', '향긋한 허브티'),
(13, 'ko', '케이크', '달콤한 케이크'),
(14, 'ko', '티라미수', '부드러운 티라미수'),
-- 피자 전문점
(15, 'ko', '페퍼로니 피자', '매콤한 페퍼로니 피자'),
(16, 'ko', '마르게리타 피자', '클래식한 마르게리타 피자'),
(17, 'ko', '까르보나라', '계란 노른자와 파마산 치즈 파스타'),
(18, 'ko', '알리오 올리오', '마늘과 올리브오일 파스타'),
(19, 'ko', '시저샐러드', '신선한 채소와 드레싱');

-- 6. 메뉴 아이템 다국어 데이터 (영어)
INSERT INTO menu_item_i18n (menu_item_id, lang, name, description) VALUES 
-- 한식당
(1, 'en', 'Kimchi Stew', 'Spicy stew made with kimchi and pork'),
(2, 'en', 'Doenjang Stew', 'Stew made with traditional soybean paste'),
(3, 'en', 'Bulgogi', 'Sweet marinated grilled pork'),
(4, 'en', 'Steamed Rice', 'A bowl of steamed rice'),
(5, 'en', 'Kimchi', 'Spicy fermented cabbage'),
(6, 'en', 'Americano', 'Clean Americano coffee'),
(7, 'en', 'Cafe Latte', 'Smooth cafe latte'),
-- 카페
(8, 'en', 'Americano', 'Clean Americano coffee'),
(9, 'en', 'Cafe Latte', 'Smooth cafe latte'),
(10, 'en', 'Cappuccino', 'Rich espresso with milk foam'),
(11, 'en', 'Lemonade', 'Refreshing lemonade'),
(12, 'en', 'Herb Tea', 'Aromatic herb tea'),
(13, 'en', 'Cake', 'Sweet cake'),
(14, 'en', 'Tiramisu', 'Smooth tiramisu'),
-- 피자 전문점
(15, 'en', 'Pepperoni Pizza', 'Spicy pepperoni pizza'),
(16, 'en', 'Margherita Pizza', 'Classic margherita pizza'),
(17, 'en', 'Carbonara', 'Pasta with egg yolk and parmesan cheese'),
(18, 'en', 'Aglio Olio', 'Pasta with garlic and olive oil'),
(19, 'en', 'Caesar Salad', 'Fresh vegetables with dressing');

-- 7. 메뉴 옵션 데이터 (중복 제거, 다양한 옵션 추가)
INSERT INTO menu_option (menu_item_id, extra_price, required) VALUES 
-- 한식당 옵션
(1, 2000, false),  -- 김치찌개 - 밥 추가
(1, 1000, false),  -- 김치찌개 - 김치 추가
(2, 2000, false),  -- 된장찌개 - 밥 추가
(3, 3000, false),  -- 불고기 - 밥 추가
-- 카페 옵션
(8, 500, false),   -- 아메리카노 - 샷 추가
(9, 500, false),   -- 카페라떼 - 샷 추가
(9, 1000, false),  -- 카페라떼 - 시럽 추가
(10, 500, false),  -- 카푸치노 - 샷 추가
(11, 1000, false), -- 레몬에이드 - 탄산 추가
-- 피자 전문점 옵션
(15, 3000, false), -- 페퍼로니 피자 - 치즈 추가
(15, 2000, false), -- 페퍼로니 피자 - 토핑 추가
(16, 3000, false), -- 마르게리타 피자 - 치즈 추가
(17, 2000, false), -- 까르보나라 - 베이컨 추가
(18, 1000, false), -- 알리오 올리오 - 페퍼론치노 추가
(19, 2000, false); -- 시저샐러드 - 치킨 추가

-- 8. 메뉴 옵션 다국어 데이터 (한국어)
INSERT INTO menu_option_i18n (menu_option_id, lang, name, description) VALUES 
-- 한식당 옵션
(1, 'ko', '밥 추가', '쌀밥 한 공기 추가'),
(2, 'ko', '김치 추가', '매콤한 김치 한 접시 추가'),
(3, 'ko', '밥 추가', '쌀밥 한 공기 추가'),
(4, 'ko', '밥 추가', '쌀밥 한 공기 추가'),
-- 카페 옵션
(5, 'ko', '샷 추가', '에스프레소 샷 하나 추가'),
(6, 'ko', '샷 추가', '에스프레소 샷 하나 추가'),
(7, 'ko', '시럽 추가', '바닐라 시럽 추가'),
(8, 'ko', '샷 추가', '에스프레소 샷 하나 추가'),
(9, 'ko', '탄산 추가', '탄산수 추가'),
-- 피자 전문점 옵션
(10, 'ko', '치즈 추가', '모짜렐라 치즈 추가'),
(11, 'ko', '토핑 추가', '다양한 토핑 추가'),
(12, 'ko', '치즈 추가', '모짜렐라 치즈 추가'),
(13, 'ko', '베이컨 추가', '바삭한 베이컨 추가'),
(14, 'ko', '페퍼론치노 추가', '매콤한 페퍼론치노 추가'),
(15, 'ko', '치킨 추가', '구운 치킨 추가');

-- 9. 메뉴 옵션 다국어 데이터 (영어)
INSERT INTO menu_option_i18n (menu_option_id, lang, name, description) VALUES 
-- 한식당 옵션
(1, 'en', 'Extra Rice', 'Add a bowl of steamed rice'),
(2, 'en', 'Extra Kimchi', 'Add a plate of spicy kimchi'),
(3, 'en', 'Extra Rice', 'Add a bowl of steamed rice'),
(4, 'en', 'Extra Rice', 'Add a bowl of steamed rice'),
-- 카페 옵션
(5, 'en', 'Extra Shot', 'Add one espresso shot'),
(6, 'en', 'Extra Shot', 'Add one espresso shot'),
(7, 'en', 'Extra Syrup', 'Add vanilla syrup'),
(8, 'en', 'Extra Shot', 'Add one espresso shot'),
(9, 'en', 'Extra Carbonation', 'Add sparkling water'),
-- 피자 전문점 옵션
(10, 'en', 'Extra Cheese', 'Add mozzarella cheese'),
(11, 'en', 'Extra Topping', 'Add various toppings'),
(12, 'en', 'Extra Cheese', 'Add mozzarella cheese'),
(13, 'en', 'Extra Bacon', 'Add crispy bacon'),
(14, 'en', 'Extra Pepperoncini', 'Add spicy pepperoncini'),
(15, 'en', 'Extra Chicken', 'Add grilled chicken');
