package org.example.gc_coffee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.gc_coffee.dto.common.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "postcode", nullable = false)
    private String postcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany (mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    // OrderProduct 추가할 때 양방향 관계 설정
    public void addOrderProduct(OrderProduct orderProduct) {
        if (!orderProducts.contains(orderProduct)) {
            orderProducts.add(orderProduct);
            orderProduct.setOrderInternal(this); // 관계 설정을 위한 내부 메서드 호출
        }
    }

    // OrderProduct 리스트를 추가할 때 양방향 관계 설정
    public void addOrderProducts(List<OrderProduct> orderProductList) {
        for (OrderProduct orderProduct : orderProductList) {
            addOrderProduct(orderProduct); // 기존 메서드를 재사용하여 처리
        }
    }

}