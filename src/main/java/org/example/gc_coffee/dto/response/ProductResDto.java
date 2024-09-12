package org.example.gc_coffee.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.gc_coffee.entity.Product;
import java.time.LocalDateTime;
import java.util.UUID;


@Schema(description = "Product api response request")
public record ProductResDto(
        UUID id,
        String name,
        String category,
        Long price,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // 정적 팩토리 메서드 of
    public static ProductResDto of(UUID id, String name, String category, Long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ProductResDto(id, name, category, price, description, createdAt, updatedAt);
    }

    // 엔티티로부터 DTO 생성하는 메서드 from
    public static ProductResDto from(Product product) {
        return new ProductResDto(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}