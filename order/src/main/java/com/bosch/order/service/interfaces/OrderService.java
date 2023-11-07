package com.bosch.order.service.interfaces;

import com.bosch.order.config.exception.NotInStockException;
import com.bosch.order.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDTO placeOrder(OrderDTO dto) throws NotInStockException;

    List<OrderDTO> findAll();
}
