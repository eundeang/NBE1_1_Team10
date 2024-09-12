package org.example.gc_coffee.Exception;

import lombok.extern.slf4j.Slf4j;
import org.example.gc_coffee.dto.common.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseDto<?> handleBadRequestException(final BadRequestException e) {
        return new ApiResponseDto<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseDto<?> handleInternalServerErrorException(final InternalServerErrorException e) {
        return new ApiResponseDto<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponseDto<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ApiResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponseDto<?> handleAllExceptions(Exception ex) {
        return new ApiResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred: " + ex.getMessage(), null);
    }

}