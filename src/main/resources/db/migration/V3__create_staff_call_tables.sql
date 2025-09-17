-- 스태프 호출 시스템 테이블 생성 및 기본 데이터 삽입

-- 1. 스태프 호출 타입 테이블 생성
CREATE TABLE staff_call_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    message VARCHAR(100) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_staff_call_type_store FOREIGN KEY (store_id) REFERENCES store(id) ON DELETE CASCADE
);

-- 2. 기존 staff_call 테이블 삭제 (새로운 구조로 재생성)
DROP TABLE IF EXISTS staff_call;

-- 3. 새로운 staff_call 테이블 생성
CREATE TABLE staff_call (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id BIGINT NOT NULL,
    call_type_id BIGINT NOT NULL,
    requested_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_staff_call_table FOREIGN KEY (table_id) REFERENCES store_table(id) ON DELETE CASCADE,
    CONSTRAINT fk_staff_call_type FOREIGN KEY (call_type_id) REFERENCES staff_call_type(id) ON DELETE CASCADE
);

-- 4. 인덱스 추가 (성능 최적화)
CREATE INDEX idx_staff_call_type_store ON staff_call_type(store_id);
CREATE INDEX idx_staff_call_table ON staff_call(table_id);
CREATE INDEX idx_staff_call_type ON staff_call(call_type_id);
CREATE INDEX idx_staff_call_requested_at ON staff_call(requested_at);

-- 5. 기본 스태프 호출 타입 데이터 삽입
INSERT INTO staff_call_type (store_id, message, active) VALUES 
-- 한식당 (store_id = 1) 기본 호출 타입
(1, '물 주세요', true),
(1, '계산해주세요', true),
(1, '자리 정리해주세요', true),
(1, '메뉴판 주세요', true),
(1, '수저 주세요', true),
(1, '김치 더 주세요', true),
(1, '물수건 주세요', true),
(1, '포장해주세요', true),

-- 카페 (store_id = 2) 기본 호출 타입
(2, '물 주세요', true),
(2, '계산해주세요', true),
(2, '자리 정리해주세요', true),
(2, '메뉴판 주세요', true),
(2, '물수건 주세요', true),
(2, '포장해주세요', true),
(2, '와이파이 비밀번호 주세요', true),
(2, '콘센트 있나요?', true),

-- 피자 전문점 (store_id = 3) 기본 호출 타입
(3, '물 주세요', true),
(3, '계산해주세요', true),
(3, '자리 정리해주세요', true),
(3, '메뉴판 주세요', true),
(3, '수저 주세요', true),
(3, '물수건 주세요', true),
(3, '포장해주세요', true),
(3, '치즈 더 주세요', true),
(3, '페퍼로니 더 주세요', true);
