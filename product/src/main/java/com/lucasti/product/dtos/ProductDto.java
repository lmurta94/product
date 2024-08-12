package com.lucasti.product.dtos;

import com.lucasti.product.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ProductDto(String description, Double price, UUID departmentId) {

    public ProductDto {

        if(description == null || description.isBlank()){
            throw new IllegalArgumentException("Name invalid");
        }

        if(price == null || price.isNaN()){
            throw new IllegalArgumentException("Price invalid");
        }
    }

    public static Product toProduct(ProductDto productDto){
        return  Product.builder()
                .description(productDto.description())
                .skuSecret("sku-product")
                .price(productDto.price())
                .build();
    }
}
