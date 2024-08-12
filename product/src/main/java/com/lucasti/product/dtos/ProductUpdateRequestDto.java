package com.lucasti.product.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ProductUpdateRequestDto(String description, Double price, UUID departmentId, MultipartFile multipartFile) {
}
