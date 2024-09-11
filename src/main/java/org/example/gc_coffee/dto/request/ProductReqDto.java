package org.example.gc_coffee.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
public class ProductReqDto {
    @NotBlank(message = "제품명을 입력해주세요.")
    private String name;

    @NotBlank(message = "카테고리를 입력해주세요.")
    private String category;

    @NotNull(message = "가격을 입력해주세요.")
    private Long price;

    @NotBlank(message = "제품 설명을 입력해주세요.")
    private String description;
}