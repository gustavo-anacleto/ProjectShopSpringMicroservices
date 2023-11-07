package com.bosch.order.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
