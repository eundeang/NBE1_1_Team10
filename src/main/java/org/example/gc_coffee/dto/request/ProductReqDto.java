package org.example.gc_coffee.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.gc_coffee.entity.Product;

import java.util.UUID;

public record ProductReqDto(
        @NotBlank(message = "제품명을 입력해주세요.")
        String name,

        @NotBlank(message = "카테고리를 입력해주세요.")
        String category,

        @NotNull(message = "가격을 입력해주세요.")
        Long price,

        @NotBlank(message = "제품 설명을 입력해주세요.")
        String description
) {
    // 정적 팩토리 메서드 of
    public static ProductReqDto of(String name, String category, Long price, String description) {
        return new ProductReqDto(name, category, price, description);
    }

    // DTO로부터 엔티티 생성하는 메서드 toEntity
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .category(category)
                .price(price)
                .description(description)
                .build();
    }

    public Product toEntity(UUID productId){
        return Product.builder()
                .id(productId)
                .name(name)
                .category(category)
                .price(price)
                .description(description)
                .build();
    }
}