package com.codecool.shop.controller;

import com.codecool.shop.exception.ResourceNotFoundException;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class ProductRestController {

    @Qualifier("productService")
    private final Service productService;

    @Autowired
    public ProductRestController(Service productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products", produces = "application/json")
    public Collection<Product> getAllProducts() {
        return productService.findAll();
    }

    // getting products with pagination
    @GetMapping(
            value = "/products",
            params = {"page", "size"},
            produces = "application/json"
    )
    public Collection<Product> getPaginatedProducts(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Product> resultPage = productService.findPaginated(page, size);
        if(page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException("Product", "page", page);
        }
        return resultPage.getContent();
    }
    @GetMapping(value = "/products/{productId}", produces = "application/json")
    public Product getProductById(@PathVariable (value="productId") Long productId) {
        Optional<Product> product = productService.findById(productId);
        if(!product.isPresent()) {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
        return product.get();
    }

    @PostMapping(value = "/products", consumes = "application/json")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.save(product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping(value = "/products/{productId}", consumes = "application/json")
    public ResponseEntity<Void> updateProduct(@PathVariable(value = "productId") Long productId, @RequestBody Product requestProduct) {
       Optional<Product> product = productService.findById(productId);
       if(!product.isPresent()) {
           throw new ResourceNotFoundException("Product", "id", productId);
       }
       requestProduct.setId(product.get().getId());
       productService.save(requestProduct);
       return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long productId) {
        Optional<Product> product = productService.findById(productId);
        if(!product.isPresent()) {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
        productService.delete(product.get());
        return ResponseEntity.noContent().build();
    }
}
