package org.example.gc_coffee.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.example.gc_coffee.dto.common.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponseDto<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ApiResponseDto<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponseDto<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ApiResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ApiResponseDto<?> handleProductAlreadyExistsException(AlreadyExistsException ex) {
        return new ApiResponseDto<>(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponseDto<?> handleAllExceptions(Exception ex) {
        return new ApiResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred: " + ex.getMessage(), null);
    }

}