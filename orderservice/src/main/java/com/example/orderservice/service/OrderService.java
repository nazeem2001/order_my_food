package com.example.orderservice.service;
import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

import com.example.orderservice.models.JWTData;
public interface OrderService {
    ResponseEntity<?> addToCart(JWTData data,String foodId);
    ResponseEntity<?> removeFromCart(JWTData data,String foodId);
    ResponseEntity<?> getCart(JWTData data);
    ResponseEntity<?> placeOrder(JWTData data,BigDecimal amount);
    ResponseEntity<?> getOrders(JWTData data);
}
