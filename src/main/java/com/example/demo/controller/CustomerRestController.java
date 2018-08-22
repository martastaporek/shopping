package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Basket;
import com.example.demo.model.Customer;
import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class CustomerRestController {

    @Qualifier("customerService")
    private final Service customerService;

    @Autowired
    public CustomerRestController(Service customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value ="/customers", produces = "application/json")
    public Collection<Customer> getAllCustomers(){
        return customerService.findAll();
    }

    @GetMapping(value = "customers/{customerId}", produces = "application/json")
    public Customer getCustomer(@PathVariable(value = "customerId") Long customerId){
        Optional<Customer> customer = customerService.findById(customerId);
        if(!customer.isPresent()){
            throw new ResourceNotFoundException("Customer", "id", customerId);
        }
        return customer.get();

    }

    @PostMapping(value = "/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        customerService.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping(value = "customers/{customerId}", produces = "application/json")
    public ResponseEntity<Void> editCustomer(@PathVariable(value = "customerId") Long customerId, @RequestBody Customer requestCustomer){
        Optional<Customer> customer = customerService.findById(customerId);
        if(!customer.isPresent()){
            throw new ResourceNotFoundException("Customer", "id", customerId);
        }
        requestCustomer.setId(customer.get().getId());
        customerService.save(requestCustomer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(value = "customerId") Long customerId){
        Optional<Customer> customer = customerService.findById(customerId);
        if(!customer.isPresent()){
            throw new ResourceNotFoundException("Customer", "id", customerId);
        }
        customerService.delete(customer.get());
        return ResponseEntity.noContent().build();
    }

}
