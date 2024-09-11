package org.example.gc_coffee.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.gc_coffee.entity.Order;
import org.example.gc_coffee.entity.OrderProduct;
import org.example.gc_coffee.entity.Product;

import java.util.UUID;

public record OrderProductReqDto(
        @NotNull(message = "상품 ID를 입력해주세요.")
        UUID productId,

        @NotBlank(message = "카테고리를 입력해주세요.")
        String category,

        @NotNull(message = "가격을 입력해주세요.")
        Long price,

        @NotNull(message = "수량을 입력해주세요.")
        Integer quantity
) {
    // 정적 팩토리 메서드 of
    public static OrderProductReqDto of(UUID productId, String category, Long price, Integer quantity) {
        return new OrderProductReqDto(productId, category, price, quantity);
    }

    // DTO로부터 엔티티 생성하는 메서드 toEntity
    public OrderProduct toEntity(Order order, Product product) {
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .category(category)
                .price(price)
                .quantity(quantity)
                .build();
    }
}