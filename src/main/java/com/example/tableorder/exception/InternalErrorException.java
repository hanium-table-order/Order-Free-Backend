package com.example.tableorder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 내부 에러 예외 (500 INTERNAL_SERVER_ERROR).
 * - 예상치 못한 오류 시 사용.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends RuntimeException {

    public InternalErrorException(String message) {
        super(message);
    }
}