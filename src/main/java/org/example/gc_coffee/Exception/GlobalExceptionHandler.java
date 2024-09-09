package org.example.gc_coffee.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.example.gc_coffee.dto.response.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonResponseDto<?>> handleEntityNotFoundException(EntityNotFoundException ex) {
        CommonResponseDto<?> response = new CommonResponseDto<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<CommonResponseDto<?>> handleProductAlreadyExistsException(AlreadyExistsException ex) {
        CommonResponseDto<?> response = new CommonResponseDto<>(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseDto<?>> handleAllExceptions(Exception ex) {
        CommonResponseDto<?> response = new CommonResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}