package com.example.demo.service;

import com.example.demo.model.Basket;
import com.example.demo.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class BasketService implements Service <Basket> {

    private BasketRepository basketRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }


    @Override
    public Collection<Basket> findAll() {
        return basketRepository.findAll();
    }

    @Override
    public Optional<Basket> findById(Long id) {
        return basketRepository.findById(id);
    }

    @Override
    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public void delete(Basket basket) {
        basketRepository.delete(basket);
    }
}
