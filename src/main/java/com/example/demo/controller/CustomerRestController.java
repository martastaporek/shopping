package com.example.demo.controller;

import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

    @Qualifier("customerService")
    private final Service customerService;

    @Autowired
    public CustomerRestController(Service customerService) {
        this.customerService = customerService;
    }
}
