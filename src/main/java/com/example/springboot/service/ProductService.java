package com.example.springboot.service;

import com.example.springboot.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductEntity createProduct(ProductEntity product);

    List<ProductEntity> findAllProducts();
}
