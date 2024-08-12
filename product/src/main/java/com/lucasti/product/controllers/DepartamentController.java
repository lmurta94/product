package com.lucasti.product.controllers;


import com.lucasti.product.dtos.DepartamentDto;
import com.lucasti.product.entities.Departament;
import com.lucasti.product.entities.Manager;
import com.lucasti.product.entities.Product;
import com.lucasti.product.repositories.DepartamentRepository;
import com.lucasti.product.repositories.ManagerRepository;
import com.lucasti.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/departament")
public class DepartamentController {

    @Autowired
    DepartamentRepository departamentRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    ResponseEntity<Departament> saveDepartament(@RequestBody DepartamentDto departamentDto){

        Set<Manager> managers = new HashSet<>(managerRepository.findAllById(departamentDto.managers()));
        Set<Product> products = new HashSet<>(productRepository.findAllById(departamentDto.products()));

        var departament = DepartamentDto.toDepartament(departamentDto);
        departament.setManagers(managers);
        departament.setProducts(products);
        departamentRepository.save(departament);

        return  ResponseEntity.status(HttpStatus.CREATED).body(departament);

    }

    @GetMapping
    public  ResponseEntity<Page<Departament>> listDepartament(@PageableDefault(
            page = 0, size = 10, sort = "description", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(departamentRepository.findAll(pageable));
    }
}
