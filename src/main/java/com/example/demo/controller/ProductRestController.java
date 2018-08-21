package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    @Qualifier("productService")
    private final Service productService;

    @Autowired
    public ProductRestController(Service productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/products")
    public ResponseEntity createProduct(@RequestBody Product product) {
        productService.save(product);

        return new ResponseEntity(product, HttpStatus.OK);
    }
}
