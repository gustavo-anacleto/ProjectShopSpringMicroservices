package com.bosch.inventory.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
