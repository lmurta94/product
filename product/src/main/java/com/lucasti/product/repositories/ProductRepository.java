package com.lucasti.product.repositories;

import com.lucasti.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT * FROM TB_PRODUCT WHERE price > :price", nativeQuery = true)
    List<Product> findByProductsByPriceBiggerThan(@Param("price") Double price);

    //FindByEagerCasosEspecificos
    @EntityGraph(attributePaths = {"departament"})
    Product findByDescription(String description);


    Page<Product> findByDepartament_DescriptionAndPriceBetween(
            String departamentDescription,
            Double minPrice,
            Double maxPrice,
            Pageable pageable);
}
