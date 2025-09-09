package com.example.tableorder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 충돌 예외 (409 CONFLICT).
 * - 중복, 재고 부족, 동시성 충돌 등에 사용.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}