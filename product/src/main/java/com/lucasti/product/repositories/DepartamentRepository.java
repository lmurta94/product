package com.lucasti.product.repositories;

import com.lucasti.product.entities.Departament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartamentRepository extends JpaRepository<Departament, UUID> {
}
