package com.lucasti.product.repositories;

import com.lucasti.product.entities.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Test
    void findByProductsByPriceBiggerThan() {
        Product product1 = createProduct("product 1", 2.0);
        Product product2 = createProduct("product 2", 3.0);

        assertTrue(productRepository.findByProductsByPriceBiggerThan(2.0)
                .stream()
                .anyMatch(product -> product.equals(product2))

        );
    }

    private Product createProduct(String name, Double price){
        Product product = Product.builder()
                .description(name)
                .price(price)
                .build();
        this.entityManager.persist(product);
        return product;
    }
}