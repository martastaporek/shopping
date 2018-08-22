package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Basket;
import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
public class BasketRestController {

    @Qualifier("basketService")
    private final Service basketService;

    @Autowired
    public BasketRestController(Service basketService) {
        this.basketService = basketService;
    }

    @GetMapping(value = "/baskets", produces = "application/json")
    public Collection<Basket> getAllBaskets() {
        return basketService.findAll();
    }

    @GetMapping(value = "/baskets/{basketid}", produces = "application/json")
    public Basket getBasketById(@PathVariable(value = "basketId") Long basketId) {
        Optional<Basket> basket = basketService.findById(basketId);
        if(!basket.isPresent()) {
            throw new ResourceNotFoundException("Basket", "id", basketId);
        }
        return basket.get();
    }
}
