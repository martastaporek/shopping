package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "baskets")
@Getter
@Setter
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBoolean")
    private boolean paid;

    @ManyToMany
    @JoinColumn(name = "fk_products")
    private List<Product> products;

    public Basket() {
    }
}
