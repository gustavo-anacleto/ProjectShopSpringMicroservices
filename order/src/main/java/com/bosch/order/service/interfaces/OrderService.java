package com.bosch.order.service.interfaces;

import com.bosch.order.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDTO placeOrder(OrderDTO dto);

    List<OrderDTO> findAll();
}
