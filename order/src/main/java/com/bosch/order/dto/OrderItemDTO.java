package com.bosch.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String id;
    @NotBlank
    private String skuCode;

    @NotNull
    @Size(min = 1)
    private BigDecimal price;

    @NotNull
    @Size(min = 1)
    private Integer quantity;
}
