package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Basket;
import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
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

    //getting baskets by pagination
    @GetMapping(
            value = "/baskets",
            params = {"page", "size"},
            produces = "application/json"
    )
    public Collection<Basket> getPaginatedBaskets(@PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Basket> resultPage = basketService.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException("Basket", "page", page);
        }
        return resultPage.getContent();
    }
    @GetMapping(value = "/baskets/{basketid}", produces = "application/json")
    public Basket getBasketById(@PathVariable(value = "basketId") Long basketId) {
        Optional<Basket> basket = basketService.findById(basketId);
        if(!basket.isPresent()) {
            throw new ResourceNotFoundException("Basket", "id", basketId);
        }
        return basket.get();
    }

    @PostMapping(value = "/baskets", consumes = "application/json")
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) {
        basketService.save(basket);

        return new ResponseEntity<>(basket, HttpStatus.OK);
    }
}
