package com.bosch.product;

import com.bosch.product.dto.ProductDTO;
import com.bosch.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongo.uri",mongoDBContainer::getReplicaSetUrl);

	}

	@Test
	void giveProductDTOWhenCallCreateMethodControllerShouldCreateProduct() throws Exception {
		ProductDTO productDTO = mock(ProductDTO.class);

		String productDTOString = objectMapper.writeValueAsString(productDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productDTOString))
				.andExpect(status().isCreated());

		Assertions.assertFalse(productRepository.findAll().isEmpty());
	}

	@Test
	void whenCallEndpointToListProductsShouldReturnListOfProducts() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

	}

}
