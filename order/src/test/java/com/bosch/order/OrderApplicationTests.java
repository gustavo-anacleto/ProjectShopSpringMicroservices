package com.bosch.order;

import com.bosch.order.dto.OrderDTO;
import com.bosch.order.dto.OrderItemDTO;
import com.bosch.order.model.OrderItem;
import com.bosch.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderApplicationTests {
	@Container
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.26"));

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
		dynamicPropertyRegistry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
		dynamicPropertyRegistry.add("spring.flyway.enabled", () -> "true");
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrderRepository orderRepository;


	@Test
	void giveOrderWhenCallInsertMethodShouldCreateOrder() throws Exception{
		OrderDTO orderDTO = getOrderDTOMock();
		String orderDTOString = objectMapper.writeValueAsString(orderDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(orderDTOString))
				.andExpect(status().isCreated());
		Assertions.assertFalse(orderRepository.findAll().isEmpty());

	}

	//NEED CORRECT THIS TEST
	@Test
	void giveWrongOrderWhenCallInsertMethodShouldReturnAValidatorError() throws Exception {
		OrderDTO orderDTO = getWrongOrderDTOMock();
		String orderDTOString = objectMapper.writeValueAsString(orderDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderDTOString))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
		assertTrue(orderRepository.findAll().isEmpty());
	}

	private OrderDTO getOrderDTOMock(){
		List<OrderItemDTO> items = new ArrayList<>();
		items.add(OrderItemDTO.builder()
				.skuCode(UUID.randomUUID().toString())
				.price(BigDecimal.valueOf(1200))
				.quantity(3).build());

		return OrderDTO.builder()
				.orderItems(items).build();
	}
	private OrderDTO getWrongOrderDTOMock(){
		List<OrderItemDTO> items = new ArrayList<>();
		items.add(OrderItemDTO.builder()
				.skuCode("")
				.price(BigDecimal.valueOf(0))
				.quantity(0).build());

		return OrderDTO.builder()
				.orderItems(items).build();
	}
}
