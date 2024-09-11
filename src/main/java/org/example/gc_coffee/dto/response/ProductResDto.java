package org.example.gc_coffee.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductResDto extends BaseResDto {
    private UUID id;
    private String name;
    private String category;
    private Long price;
    private String description;
}