package org.example.gc_coffee.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.gc_coffee.entity.Order;
import org.example.gc_coffee.entity.OrderProduct;
import org.example.gc_coffee.entity.Product;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "OrderProduct api response request")
public record OrderProductResDto(
        Long seq,
        UUID orderId,
        UUID productId,
        String category,
        Long price,
        Integer quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // 정적 팩토리 메서드 of
    public static OrderProductResDto of(Long seq, UUID orderId, UUID productId, String category, Long price, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new OrderProductResDto(seq, orderId, productId, category, price, quantity, createdAt, updatedAt);
    }

    // 엔티티로부터 DTO 생성하는 메서드 from
    public static OrderProductResDto from(OrderProduct orderProduct) {
        return new OrderProductResDto(
                orderProduct.getSeq(),
                orderProduct.getOrder().getId(),
                orderProduct.getProduct().getId(),
                orderProduct.getCategory(),
                orderProduct.getPrice(),
                orderProduct.getQuantity(),
                orderProduct.getCreatedAt(),
                orderProduct.getUpdatedAt()
        );
    }

}