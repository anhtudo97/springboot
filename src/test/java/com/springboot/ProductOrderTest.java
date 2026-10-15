package com.springboot;

import com.springboot.entity.OrderEntity;
import com.springboot.entity.ProductEntity;
import com.springboot.repository.OrderRepository;
import com.springboot.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@SpringBootTest
public class ProductOrderTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    @Rollback(false)
    void manyToManyInsertTest() {
        ProductEntity p1 = new ProductEntity();
        ProductEntity p2 = new ProductEntity();

        OrderEntity o1 = new OrderEntity();
        OrderEntity o2 = new OrderEntity();
        OrderEntity o3 = new OrderEntity();

        p1.setProductName("p1");
        p1.setProductPrice(new BigDecimal("4.6"));
        p2.setProductName("p2");
        p2.setProductPrice(new BigDecimal("4.7"));

        o1.setUserId(1);
        o2.setUserId(2);
        o3.setUserId(3);

        // set list in product
        p1.setOrderList(List.of(o1, o2));
        p2.setOrderList(List.of(o1, o2, o3));

        orderRepository.save(o1);
        orderRepository.save(o2);
        orderRepository.save(o3);

        productRepository.save(p1);
        productRepository.save(p2);
    }

    @Test
    @Transactional
    void selectManyToManyTest() {
        ProductEntity p1 = productRepository.findById(1L).orElseThrow();
        log.info(String.valueOf(p1));
        log.info(String.valueOf(p1.getOrderList()));
    }
}
