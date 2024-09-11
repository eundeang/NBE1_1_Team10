package org.example.gc_coffee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @Column(name = "order_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "postcode", nullable = false)
    private String postcode;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @OneToMany (mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    // OrderProduct 추가할 때 양방향 관계 설정
    public void addOrderProduct(OrderProduct orderProduct) {
        if (!orderProducts.contains(orderProduct)) {
            orderProducts.add(orderProduct);
            orderProduct.setOrderInternal(this); // 관계 설정을 위한 내부 메서드 호출
        }
    }
}