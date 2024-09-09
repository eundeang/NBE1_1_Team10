package org.example.gc_coffee.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderProductDto extends BaseDto {
    private Long seq;
    private UUID orderId;
    private UUID productId;
    private String category;
    private Long price;
    private Integer quantity;
}