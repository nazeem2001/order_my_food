package com.example.orderservice.models;

import java.math.BigDecimal;
import java.util.List;

import com.example.orderservice.entity.Food;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class CartResponce {
    List<Food>items;
    BigDecimal amount;

}
