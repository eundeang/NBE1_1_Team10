package org.example.gc_coffee.Exception;

import lombok.Getter;

/**
 * 500 번대 서버 오류 매핑
 */
@Getter
public class InternalServerErrorException extends RuntimeException {

    private final int code;
    private final String message;

    public InternalServerErrorException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}