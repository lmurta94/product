//package com.lucasti.product.services;
//
//import com.lucasti.product.dtos.ProductDto;
//import com.lucasti.product.entities.Product;
//import com.lucasti.product.repositories.ProductRepository;
//import com.lucasti.product.specifications.ProductSpecifications;
//import net.kaczmarzyk.spring.data.jpa.domain.DateBefore;
//import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
//import net.kaczmarzyk.spring.data.jpa.domain.Like;
//import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
//import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class ProductServiceTest {
//
////    @Mock
//    @Autowired
//    private ProductRepository productRepository;
//
//
////    @Mock
////    @InjectMocks
//    @Autowired
//    private ProductService productService;
//
//
//    @Autowired
//    private ProductSpecifications productSpecifications;
//
//    @BeforeEach
//    void setUp() {
////        MockitoAnnotations.initMocks(this);
//    }
//
////    @Test
////    @DisplayName("Should get product  from  database")
////    void getProductSuccess() {
////
////
////        Pageable pageable = PageRequest.of(0,10);
////        UUID uuid = UUID.fromString("c8ab98db-f4fa-4370-8b2b-cf6e5b82a233");
////
////        productService.saveProduct(new ProductDto("Bala",2.0, uuid));
////        assertTrue(productService.getAllProducts(pageable)
////                .stream()
////                .anyMatch(p -> "Bala".equalsIgnoreCase(p.description()))
////        );
////    }
//}