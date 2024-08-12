package com.lucasti.product.dtos.kafka.producer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ProductProducerDTO
        (UUID id, String description, Double price, String sku,String status, LocalDateTime createAt) {}


