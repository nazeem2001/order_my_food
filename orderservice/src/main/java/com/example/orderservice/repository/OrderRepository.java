package com.example.orderservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.orderservice.entity.Orders;

public interface OrderRepository extends MongoRepository<Orders,String> {
    List<Orders> findAllByUserId(String userid);
}
