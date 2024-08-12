package com.lucasti.product.controllers;

import com.lucasti.product.dtos.ProductDto;
import com.lucasti.product.dtos.ProductUpdateRequestDto;
import com.lucasti.product.dtos.responses.ProductsReponseDTO;
import com.lucasti.product.entities.Product;
import com.lucasti.product.services.ProductService;
import com.lucasti.product.specifications.ProductSpecifications;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/product")
public class ProductController {

    ExecutorService executorServiceVirtual = Executors.newVirtualThreadPerTaskExecutor();

    ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @CacheEvict(value = "products", allEntries = true)
    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody ProductDto productDto){

        var product = productService.saveProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @CacheEvict(value = "products", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@ModelAttribute ProductUpdateRequestDto productDto, @PathVariable(value = "id") UUID id)  {
        var productDatabase = productService.getProductById(id);
        var productUpdated = productService.updateProduct(productDatabase, productDto);
        return new ResponseEntity<>(productUpdated, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable(value = "id") UUID id){
        var product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }


    @GetMapping("/list-products")
    public ResponseEntity<Page<ProductsReponseDTO>> listProduct(@PageableDefault(
            page = 0, size = 10, sort = "price", direction = Sort.Direction.ASC) Pageable pageable,
                                                                Specification<Product> spec) {

        System.out.println(Thread.currentThread());
        Page<ProductsReponseDTO> productsPage = productService.getAllProducts(pageable, spec);
        return  ResponseEntity.status(HttpStatus.OK).body(productsPage);
    }



    @GetMapping("/list-products-v2")
    public CompletableFuture<Page<Product>> listProduct2(@PageableDefault(
            page = 0, size = 10, sort = "price", direction = Sort.Direction.ASC) Pageable pageable){


        return  CompletableFuture.supplyAsync(()-> productService.getAllProductAsync(pageable), executorServiceVirtual);
    }



    @GetMapping("/list-products-v3")
    public Page<Product> listProduct3(@PageableDefault(
            page = 0, size = 10, sort = "price", direction = Sort.Direction.ASC) Pageable pageable){
        return  productService.getAllVirtual(pageable);
    }


}
