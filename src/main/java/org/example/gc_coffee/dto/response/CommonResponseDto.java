package org.example.gc_coffee.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CommonResponseDto<T> {
    private Integer status;
    private String message;
    private T data;
    private LocalDateTime timeStamp;

    public CommonResponseDto(String message, T data) {
        this.status = HttpStatus.OK.value();
        this.message = message;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }

    public CommonResponseDto(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }


    public HttpStatus getHttpStatus() {
        try{
            return HttpStatus.valueOf(status);
        }catch (IllegalArgumentException e){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
