package com.bosch.product.service.interfaces;

import com.bosch.product.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductDTO createProduct(ProductDTO dto);

    List<ProductDTO> findAll();
}
