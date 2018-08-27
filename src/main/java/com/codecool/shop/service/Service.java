package com.codecool.shop.service;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Optional;

public interface Service<T> {

    Collection <T> findAll();
    Optional<T> findById(Long id);
    T save(T object);
    void delete(T object);
    Page<T> findPaginated(int page, int size);
}
