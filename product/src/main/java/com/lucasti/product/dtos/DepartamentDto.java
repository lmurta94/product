package com.lucasti.product.dtos;

import com.lucasti.product.entities.Departament;

import java.util.Set;
import java.util.UUID;

public record DepartamentDto(String name, Set<UUID> products, Set<UUID> managers) {

    public static Departament toDepartament(DepartamentDto departamentDto){
        return  Departament
                .builder()
                .description(departamentDto.name())
                .build();
    }
}
