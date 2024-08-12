package com.lucasti.product.specifications;

import com.lucasti.product.entities.Departament;
import com.lucasti.product.entities.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import net.kaczmarzyk.spring.data.jpa.domain.DateBefore;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductSpecifications {


    //Maneira do curso do criteria builder da micheli brito
    @And({
            @Spec(path = "description", spec = Like.class),
            @Spec(path = "price", spec = GreaterThan.class),
            @Spec(path = "createAt", spec = DateBefore.class)
    })
    public interface ProductSpec extends Specification<Product> {}


    public static Specification<Product> filterByDepartamentAndDescription(String departamentName, String description) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (departamentName != null && !departamentName.isEmpty()) {
                Join<Product, Departament> departamentJoin = root.join("departament");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(departamentJoin.get("description"), departamentName));
            }

            if (description != null && !description.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("description"), "%" + description + "%"));
            }

            return predicate;
        };
    }

    public static Specification<Product> filterProductByPriceOver(Double price) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate =  criteriaBuilder.conjunction();

            if (price != null) {
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price))
                        .isNotNull();
            }

            return predicate;
        };
    }

}