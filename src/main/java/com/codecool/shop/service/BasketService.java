package com.codecool.shop.service;

import com.codecool.shop.model.Basket;
import com.codecool.shop.model.Customer;
import com.codecool.shop.repository.BasketRepository;
import com.codecool.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
@Qualifier("basketService")
public class BasketService implements Service <Basket> {

    private BasketRepository basketRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository, CustomerRepository customerRepository) {
        this.basketRepository = basketRepository;
        this.customerRepository = customerRepository;
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

        Customer customer = basket.getCustomer();
        customer.getBaskets().remove(basket);
        customerRepository.save(customer);
        basketRepository.delete(basket);
    }

    @Override
    public Page<Basket> findPaginated(int page, int size) {
        return basketRepository.findAll(PageRequest.of(page, size));
    }
}
