package com.lucasti.product.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;
import java.util.UUID;


@Table(name = "TB_DEPARTAMENT")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"products", "managers"})
public class Departament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id ;

    @Column(name = "description")
    private String description;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "departament", fetch = FetchType.LAZY)
    //Padrao jois, se digitar ele ignora o tipo Lazy(Se n, nao ignora
    //Select faz uma consulta pra cada manager
    //SubSelect faz uma consulta pra departamento e uma pra manager
    @Fetch(FetchMode.SUBSELECT)
//    @JsonManagedReference
    private Set<Product> products;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_DEPARTAMENT_MANAGER",
            joinColumns = @JoinColumn(name = "departament_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private Set<Manager> managers;
}
