package com.lucasti.email.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductReceiverDTO
        (UUID id, String description, Double price, String sku, String status, LocalDateTime createAt) {}
