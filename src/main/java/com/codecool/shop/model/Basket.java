package com.codecool.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "baskets")
@Getter
@Setter
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "basket_id")
    private Long id;

    private boolean paid = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="products_in_baskets",
            joinColumns = @JoinColumn(name="basket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    public Basket() {
    }
}
