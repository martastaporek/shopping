package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;

public interface Service<T> {

    Collection <T> findAll();
    Optional<T> findById(Long id);
    T save(T object);
    void delete(T object);
}
