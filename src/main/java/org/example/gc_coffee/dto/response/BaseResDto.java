package org.example.gc_coffee.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseResDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
