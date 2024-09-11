package org.example.gc_coffee.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.example.gc_coffee.dto.common.OrderStatus;
import org.example.gc_coffee.entity.Order;
import org.example.gc_coffee.entity.OrderProduct;

import java.util.List;

public record OrderReqDto(
        @NotBlank(message = "이메일을 입력해주세요.")
        @Pattern(
                regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$",
                message = "이메일 주소를 확인해주세요")
        String email,

        @NotBlank(message = "주소 정보를 입력해주세요.")
        String address,

        @NotBlank(message = "우편번호 정보를 입력해주세요.")
        String postcode,

        @NotNull(message = "주문 상품이 없습니다.")
        @Valid
        List<OrderProductReqDto> orderProductList
) {
    // 정적 팩토리 메서드 of
    public static OrderReqDto of(String email, String address, String postcode, List<OrderProductReqDto> orderProductList) {
        return new OrderReqDto(email, address, postcode, orderProductList);
    }

    // DTO로부터 Order 엔티티 생성하는 메서드 toEntity
    public Order toEntity(List<OrderProduct> orderProducts) {
        // 주문 엔티티 생성
        Order order = Order.builder()
                .email(email)
                .address(address)
                .postcode(postcode)
                .orderStatus(OrderStatus.ORDER_PLACED)
                .orderProducts(orderProducts)
                .build();

        // 양방향 연관 관계 설정
        order.addOrderProducts(orderProducts);

        return order;
    }

    // DTO로부터 Order 엔티티 생성하는 메서드 toEntity
    public Order toEntity() {
        // 주문 엔티티 생성
        Order order = Order.builder()
                .email(email)
                .address(address)
                .postcode(postcode)
                .orderStatus(OrderStatus.ORDER_PLACED)
                .build();

        return order;
    }
}