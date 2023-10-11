package com.bosch.order.service.classes;

import com.bosch.order.dto.OrderDTO;
import com.bosch.order.model.Order;
import com.bosch.order.model.OrderItem;
import com.bosch.order.repository.OrderRepository;
import com.bosch.order.service.interfaces.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public OrderDTO placeOrder(OrderDTO dto) {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderItems(dto.getOrderItems().stream()
                .map(orderItemDTO -> modelMapper.map(orderItemDTO, OrderItem.class)).toList());

        // Call Inventory Service, and place order if product is in
        // stock
        order = orderRepository.save(order);

        return modelMapper.map(order,OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDTO.class)).toList();
    }

}
