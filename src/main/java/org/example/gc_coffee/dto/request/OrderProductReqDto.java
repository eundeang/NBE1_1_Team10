package org.example.gc_coffee.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class OrderProductReqDto {
    @NotNull(message = "상품 ID를 입력해주세요.")
    private UUID productId;

    @NotBlank(message = "카테고리를 입력해주세요.")
    private String category;

    @NotNull(message = "가격을 입력해주세요.")
    private Long price; // 클라이언트가 보내면 DB 통신 1회 사라짐

    @NotNull(message = "수량을 입력해주세요.")
    private Integer quantity;
}