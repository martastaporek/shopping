package com.example.demo.service;

import java.util.Collection;

public interface Service<T> {

    Collection <T> findAll();
    T findById(Long id);
    T save(T object);
    void delete(T object);
}
