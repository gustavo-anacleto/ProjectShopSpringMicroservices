package com.bosch.order.controller;

import com.bosch.order.config.exception.NotInStockException;
import com.bosch.order.dto.OrderDTO;
import com.bosch.order.service.interfaces.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody @Valid OrderDTO dto) throws NotInStockException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(dto));
    }
}
