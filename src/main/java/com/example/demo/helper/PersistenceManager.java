package com.example.demo.helper;

import com.example.demo.model.Basket;
import com.example.demo.model.Category;
import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.repository.BasketRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.service.Service;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@ConditionalOnProperty(name = "shopdb-init", havingValue = "true")
public class PersistenceManager implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;

    @Autowired
    public PersistenceManager(CustomerRepository customerRepository, ProductRepository productRepository, BasketRepository basketRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.basketRepository = basketRepository;
    }

    @Override
    public void run(String... args) {
        generateCustomers();
    }


    public void generateCustomers(){

        Faker faker = new Faker();

        for(int i = 0; i < 10; i++){
            Customer fakeCustomer = new Customer();
            fakeCustomer.setName(faker.gameOfThrones().character());
            if(i%2==0){
                Product fakeProduct = new Product();
                fakeProduct.setName(faker.food().spice());
                fakeProduct.setCategory(Category.FOOD);
                fakeProduct.setPrice(i*10);
                productRepository.save(fakeProduct);

                List<Product> products = new ArrayList<>();
                products.add(fakeProduct);
                Basket fakeBasket = new Basket();
                fakeBasket.setProducts(products);
                List<Basket>baskets = new ArrayList<>();
                baskets.add(fakeBasket);
                fakeCustomer.setBaskets(baskets);
            }
            this.customerRepository.save(fakeCustomer);


        }

    }
}
