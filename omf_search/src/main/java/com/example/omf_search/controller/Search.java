package com.example.omf_search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.omf_search.service.UserService;

@RestController
@RequestMapping("/search/")
public class Search {
    @Autowired
    UserService service;
    @GetMapping("/")
    String f1(){
        return "hello";
    }
    @GetMapping("/search-location")
    public ResponseEntity<?> searchByLocation(@RequestParam(name = "location") String Location){
        return service.searchByLocation(Location);
    }
    @GetMapping("/search-price")
    public ResponseEntity<?> searchByprice(@RequestParam(name = "price") String price){
        return service.searchByprice(price);
    }
    @GetMapping("/search-distance")
    public ResponseEntity<?> searchByDistance(@RequestParam(name = "distance") String Distance){
        return service.searchByDistance(Distance);
    }
    @GetMapping("/search-cusine")
    public ResponseEntity<?> searchByCusine(@RequestParam(name = "cusine") String Cusine){
        return service.searchByCusine(Cusine);
    }
    @GetMapping("/search-RestroName")
    public ResponseEntity<?> searchByRestroName(@RequestParam(name = "RestroName") String RestroName){
        return service.searchByRestroName(RestroName);
    }
}
