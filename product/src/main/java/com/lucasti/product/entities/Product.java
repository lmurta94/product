package com.lucasti.product.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lucasti.product.enums.StatusProduct;
import com.lucasti.product.services.CryptoService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_PRODUCT")
@ToString(exclude = {"departament", "image"})
@EqualsAndHashCode(of = "id")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "description", nullable = false, unique = true)
    private String description;
    @Column(name = "price", precision = 2)
    private Double price;
    @Column(name = "sku")
    private String skuSecret;
    @Enumerated(EnumType.STRING)
    private StatusProduct status;
    @Lob
    @JsonIgnore
    private byte[] image;
    @CreationTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;
    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "departament_id") Define o nome da tabela gerada(opcional)
    private Departament departament;

    @PrePersist
    public void prePersist() {
        this.skuSecret = CryptoService.encrypt(this.skuSecret);
    }

    @PostPersist
    public void postLoad() {
        this.skuSecret = CryptoService.decrypt(this.skuSecret);
    }
}
