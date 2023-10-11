package com.bosch.inventory.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface InventoryService {
    Boolean isInStock(String skuCode);
}
