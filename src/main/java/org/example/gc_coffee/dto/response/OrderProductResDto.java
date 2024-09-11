package org.example.gc_coffee.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderProductResDto extends BaseResDto {
    private Long seq;
    private UUID orderId;
    private UUID productId;
    private String category;
    private Long price;
    private Integer quantity;
}