package com.example.orderservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.orderservice.entity.Cart;

public interface CartRepository extends MongoRepository<Cart,String> {
    
}
