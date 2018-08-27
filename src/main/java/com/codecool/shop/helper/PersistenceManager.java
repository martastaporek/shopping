package com.codecool.shop.helper;

import com.codecool.shop.model.Basket;
import com.codecool.shop.repository.BasketRepository;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.codecool.shop.repository.CustomerRepository;
import com.codecool.shop.repository.ProductRepository;
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
    private final Faker faker = new Faker();

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


    private void generateCustomers(){

        for(int i = 0; i < 10; i++){
            Customer fakeCustomer = new Customer();
            fakeCustomer.setName(faker.gameOfThrones().character());
            customerRepository.save(fakeCustomer);
            List<Product> products;
            if(i%2==0){
                products = generateFood();

            }else {
                products = generateBooks();

            }
            setProductsInBasket(fakeCustomer, products);
            this.customerRepository.save(fakeCustomer);


        }

    }

    private void setProductsInBasket(Customer customer, List<Product> products){

        Basket fakeBasket = new Basket();
        fakeBasket.setProducts(products);
        fakeBasket.setCustomer(customer);
        basketRepository.save(fakeBasket);
        List<Basket>baskets = new ArrayList<>();
        baskets.add(fakeBasket);
        customer.setBaskets(baskets);

    }

    private List <Product> generateFood(){

        List<Product> food = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            Product fakeProduct = new Product();
            fakeProduct.setName(faker.food().spice());
            fakeProduct.setCategory(Category.FOOD);
            fakeProduct.setPrice(i*10);
            productRepository.save(fakeProduct);
            food.add(fakeProduct);
        }
        return food;
    }

    private List <Product> generateBooks(){

        List<Product> books = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            Product fakeProduct = new Product();
            fakeProduct.setName(faker.book().title());
            fakeProduct.setCategory(Category.BOOK);
            fakeProduct.setPrice(i*10);
            productRepository.save(fakeProduct);
            books.add(fakeProduct);
        }
        return books;
    }


}
