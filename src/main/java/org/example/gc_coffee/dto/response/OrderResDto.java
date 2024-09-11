package org.example.gc_coffee.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderResDto extends BaseResDto {
    private UUID id;
    private String email;
    private String address;
    private String postcode;
    private String orderStatus;
    private List<OrderProductResDto> orderProducts; // 주문 항목 목록
}