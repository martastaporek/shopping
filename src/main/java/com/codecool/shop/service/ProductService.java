package com.codecool.shop.service;

import com.codecool.shop.model.Product;
import com.codecool.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
@Qualifier("productService")
public class ProductService implements Service<Product> {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Collection<Product> findAll() {
        return productRepository.findAllByAvailableTrue();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findByIdAndAvailableTrue(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {

        product.setAvailable(false);
        productRepository.save(product);
    }

    @Override
    public Page<Product> findPaginated(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }
}
