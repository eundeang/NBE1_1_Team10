package org.example.gc_coffee.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.gc_coffee.dto.common.OrderStatus;
import org.example.gc_coffee.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Order api response request")
public record OrderResDto(
        UUID id,
        String email,
        String address,
        String postcode,
        OrderStatus orderStatus,
        List<OrderProductResDto> orderProductList, // 주문 항목 목록
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // 정적 팩토리 메서드 of
    public static OrderResDto of(UUID id, String email, String address, String postcode, OrderStatus orderStatus, List<OrderProductResDto> orderProducts, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new OrderResDto(id, email, address, postcode, orderStatus, orderProducts, createdAt, updatedAt);
    }

    // 엔티티로부터 DTO 생성하는 메서드 from
    public static OrderResDto from(Order order) {
        List<OrderProductResDto> orderProductDtos = order.getOrderProducts().stream()
                .map(OrderProductResDto::from) // 각 OrderProduct 엔티티를 OrderProductResDto로 변환
                .toList();

        return new OrderResDto(
                order.getId(),
                order.getEmail(),
                order.getAddress(),
                order.getPostcode(),
                order.getOrderStatus(),
                orderProductDtos,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

}