package com.bosch.inventory.controller;

import com.bosch.inventory.dto.InventoryResponse;
import com.bosch.inventory.service.interfaces.InventoryService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCode){
        return ResponseEntity.ok(inventoryService.isInStock(skuCode));
    }
}
