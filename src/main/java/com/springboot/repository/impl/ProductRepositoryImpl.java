package com.springboot.repository.impl;

import com.springboot.entity.ProductEntity;
import com.springboot.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public ProductEntity createProduct(ProductEntity product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setProductName("Tuanh");
        productEntity.setProductPrice(new BigDecimal("22.3"));
        return productEntity;
    }

    @Override
    public List<ProductEntity> findAllProducts() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setProductName("Tuanh");
        productEntity.setProductPrice(new BigDecimal("22.3"));
        return List.of(productEntity);
    }
}
