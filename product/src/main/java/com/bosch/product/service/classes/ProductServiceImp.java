package com.bosch.product.service.classes;

import com.bosch.product.dto.ProductDTO;
import com.bosch.product.model.Product;
import com.bosch.product.repository.ProductRepository;
import com.bosch.product.service.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = modelMapper.map(dto,Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
    }
}
