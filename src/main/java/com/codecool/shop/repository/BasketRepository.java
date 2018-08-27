package com.codecool.shop.repository;

import com.codecool.shop.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

}

