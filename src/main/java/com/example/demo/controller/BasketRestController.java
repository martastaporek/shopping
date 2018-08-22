package com.example.demo.controller;

import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketRestController {

    @Qualifier("basketService")
    private final Service basketService;

    @Autowired
    public BasketRestController(Service basketService) {
        this.basketService = basketService;
    }
}