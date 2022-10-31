package com.example.omf_search.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.omf_search.entity.Food;

public interface UserService {
    ResponseEntity<?> searchByLocation(String location);
    ResponseEntity<?> searchByRestroName(String location);
    ResponseEntity<?> searchByCusine(String location);
   ResponseEntity<?> searchByDistance(String location);
   ResponseEntity<?> searchByprice(String location);
}  