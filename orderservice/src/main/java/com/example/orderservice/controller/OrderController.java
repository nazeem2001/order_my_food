package com.example.orderservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.security.JwtTokenUtil;
import com.example.orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/hi")
    public String hello(){
        return "hello";
    }
    @PutMapping("add-to-cart")
    public ResponseEntity<?> addToCart(@RequestHeader(value = "Authorization") String authorizationHeader
    ,@RequestParam(name = "food-id") String foodid){
        try {
            return orderService.addToCart(jwtTokenUtil.validateToken(authorizationHeader), foodid);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }        
    }
    @PutMapping("/remove-cart")
    public ResponseEntity<?> removeFromCart(@RequestHeader(value = "Authorization") String authorizationHeader
    ,@RequestParam(name = "food-id") String foodid){
        try {
            return orderService.removeFromCart(jwtTokenUtil.validateToken(authorizationHeader), foodid);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
            
    }
    @GetMapping("get-cart")
    public ResponseEntity<?> getCart(@RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            return orderService.getCart(jwtTokenUtil.validateToken(authorizationHeader));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
            
    }
    @GetMapping("get-orders")
    public ResponseEntity<?> getOrders(@RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            return orderService.getOrders(jwtTokenUtil.validateToken(authorizationHeader));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestHeader(value = "Authorization") String authorizationHeader
    ,@RequestParam(name = "amount") BigDecimal amount){
        try {
            return orderService.placeOrder(jwtTokenUtil.validateToken(authorizationHeader), amount);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
            
    }
}
