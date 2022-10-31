package com.example.omf_search.iservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.omf_search.entity.Food;
import com.example.omf_search.repository.FoodRepository;
import com.example.omf_search.service.UserService;


@Service
public class userImpl implements UserService{
    @Autowired
    FoodRepository foodRepository;

    @Override
    public ResponseEntity<?> searchByLocation(String location) {
        List<Food> lfod=foodRepository.findByLocation(location);
        if(!lfod.isEmpty()){
            return new ResponseEntity<>(lfod, HttpStatus.OK);
        }

        return new ResponseEntity<>("No food found", HttpStatus.BAD_REQUEST);
    }
    @Override
    public ResponseEntity<?> searchByRestroName(String location) {
        List<Food> lfod=foodRepository.findByRestoName(location);
        if(!lfod.isEmpty()){
            return new ResponseEntity<>(lfod, HttpStatus.OK);
        }

        return new ResponseEntity<>("No food found", HttpStatus.BAD_REQUEST);
    }
    @Override
    public ResponseEntity<?> searchByCusine(String location) {
        List<Food> lfod=foodRepository.findByCusine(location);
        if(!lfod.isEmpty()){
            return new ResponseEntity<>(lfod, HttpStatus.OK);
        }

        return new ResponseEntity<>("No food found", HttpStatus.BAD_REQUEST);
    }
    @Override
    public ResponseEntity<?> searchByDistance(String location) {
        List<Food> lfod=foodRepository.findByDistance(location);
        if(!lfod.isEmpty()){
            return new ResponseEntity<>(lfod, HttpStatus.OK);
        }

        return new ResponseEntity<>("No food found", HttpStatus.BAD_REQUEST);
   }
    @Override
    public ResponseEntity<?> searchByprice(String location) {
        List<Food> lfod=foodRepository.findByPrice(location);
        if(!lfod.isEmpty()){
            return new ResponseEntity<>(lfod, HttpStatus.OK);
        }

        return new ResponseEntity<>("No food found", HttpStatus.BAD_REQUEST);

    }
}
