package com.bosch.inventory.service.interfaces;

import com.bosch.inventory.dto.InventoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
