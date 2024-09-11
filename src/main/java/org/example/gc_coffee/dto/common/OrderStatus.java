package org.example.gc_coffee.dto.common;

public enum OrderStatus {
    ORDER_PLACED,        // 주문 완료
    PENDING_PAYMENT,     // 결제 대기
    PAYMENT_COMPLETED,   // 결제 완료
    PREPARING_SHIPMENT,  // 상품 준비 중
    SHIPPED,             // 배송 중
    DELIVERED,           // 배송 완료
    COMPLETED            // 주문 완료
    ;
}
