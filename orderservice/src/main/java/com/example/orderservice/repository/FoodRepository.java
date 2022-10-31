package com.example.orderservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orderservice.entity.Food;


@Repository
public interface FoodRepository extends JpaRepository<Food,String> {
    List<Food> findByLocation(String location);
    List<Food> findByRestoName (String restName);
    List<Food> findByDistance(String location);
    List<Food> findByCusine(String location);
    List<Food> findByPrice(String location);
}
