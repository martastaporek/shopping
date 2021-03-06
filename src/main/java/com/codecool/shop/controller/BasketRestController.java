package com.codecool.shop.controller;

import com.codecool.shop.model.Basket;
import com.codecool.shop.service.Service;
import com.codecool.shop.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //getting baskets by pagination
    @GetMapping(
            value = "/baskets",
            params = {"page", "size"},
            produces = "application/json"
    )
    public Collection<Basket> getPaginatedBaskets(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Basket> resultPage = basketService.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException("Basket", "page", page);
        }
        return resultPage.getContent();
    }
    @GetMapping(value = "/baskets/{basketId}", produces = "application/json")
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

    @PutMapping(value = "/baskets/{basketId}", consumes = "application/json")
    public ResponseEntity<Void> updateBasket(@PathVariable(value = "basketId") Long basketId, @RequestBody Basket requestBasket) {
        Optional<Basket> basket = basketService.findById(basketId);
        if(!basket.isPresent()) {
            throw new ResourceNotFoundException("Basket", "id", basketId);
        }

        requestBasket.setId(basket.get().getId());
        basketService.save(requestBasket);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/baskets/{id}")
    public ResponseEntity<?> deleteBasket(@PathVariable(value = "id") Long basketId) {
        Optional<Basket> basket = basketService.findById(basketId);
        if(!basket.isPresent()) {
            throw new ResourceNotFoundException("Basket", "id", basketId);
        }
        basketService.delete(basket.get());
        return ResponseEntity.noContent().build();
    }
}
