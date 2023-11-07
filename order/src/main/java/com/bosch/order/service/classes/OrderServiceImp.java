package com.bosch.order.service.classes;

import com.bosch.order.config.exception.NotInStockException;
import com.bosch.order.dto.InventoryResponse;
import com.bosch.order.dto.OrderDTO;
import com.bosch.order.model.Order;
import com.bosch.order.model.OrderItem;
import com.bosch.order.repository.OrderRepository;
import com.bosch.order.service.interfaces.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public OrderDTO placeOrder(OrderDTO dto) throws NotInStockException {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderItems(dto.getOrderItems().stream()
                .map(orderItemDTO -> modelMapper.map(orderItemDTO, OrderItem.class)).toList());

        // Call Inventory Service, and place order if product is in
        // stock
        Boolean result = validateOrderItemsInStock(order);

        order = orderRepository.save(order);

        return modelMapper.map(order,OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDTO.class)).toList();
    }

    private Boolean validateOrderItemsInStock(Order order) throws  NotInStockException {

        List<String> skuCodes = order.getOrderItems().stream().map(OrderItem::getSkuCode).toList();



        InventoryResponse[] inventoryResponses = webClient.build().get()
                .uri("http://inventory-ms/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if(inventoryResponses == null)
            throw new NotInStockException("No list of products in stock");

        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(!allProductsInStock)
            throw new NotInStockException("Product is not in stock");

        return true;
    }
}
