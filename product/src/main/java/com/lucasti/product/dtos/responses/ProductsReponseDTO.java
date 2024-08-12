package com.lucasti.product.dtos.responses;

import com.lucasti.product.entities.Product;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record ProductsReponseDTO (UUID id, String description, Double price, String departamentName)
        implements Serializable {

    public static ProductsReponseDTO toProductResponseDTO(Product product){
        return  ProductsReponseDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .departamentName(product.getDepartament().getDescription())
                .build();
    }
}


