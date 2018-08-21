package com.example.demo.controller;

import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    @Qualifier("productService")
    private final Service productService;

    @Autowired
    public ProductRestController(Service productService) {
        this.productService = productService;
    }
}
