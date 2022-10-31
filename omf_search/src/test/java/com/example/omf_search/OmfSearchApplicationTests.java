package com.example.omf_search;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import com.example.omf_search.entity.Food;
import com.example.omf_search.iservice.userImpl;
import com.example.omf_search.repository.FoodRepository;

@ExtendWith(MockitoExtension.class)
class OmfSearchApplicationTests {
    @Mock
    FoodRepository foodRepositoryMock;
    @InjectMocks
    userImpl search;
    private Food food;
    private List<Food> emptyList;
    private List<Food> filledList;
    

    @BeforeEach
    void setup() {
        food = new Food("1", "my food", "good food", "my resto", "7.0", "89", "my cusine", "my Home");
        emptyList = new ArrayList<>();
        filledList = new ArrayList<Food>();
        filledList.add(food);
    }

    @Test
    void testSearchByLocation() {
        when(foodRepositoryMock.findByLocation("my Home")).thenReturn(filledList);
        ResponseEntity<?> responseEntity = search.searchByLocation("my Home");
        if (!(responseEntity.getStatusCodeValue() == 200)) {
            Assertions.fail();
        }
    }
    @Test
    void testSearchByLocationFailed() {
        when(foodRepositoryMock.findByLocation("my Home")).thenReturn(emptyList);
        ResponseEntity<?> responseEntity = search.searchByLocation("my Home");
        if (!(responseEntity.getStatusCodeValue() == 400)) {
            Assertions.fail();
        }
    }

    @Test
    void testSearchByCusine() {
        when(foodRepositoryMock.findByCusine("my cusine")).thenReturn(filledList);
        ResponseEntity<?> responseEntity = search.searchByCusine("my cusine");
        if (!(responseEntity.getStatusCodeValue() == 200)) {
            Assertions.fail();
        }
    }
    @Test
    void testSearchByCusineFailed() {
        when(foodRepositoryMock.findByCusine("my cusine")).thenReturn(emptyList);
        ResponseEntity<?> responseEntity = search.searchByCusine("my cusine");
        if (!(responseEntity.getStatusCodeValue() == 400)) {
            Assertions.fail();
        }
    }


    @Test
    void testSearchByDistance() {
        when(foodRepositoryMock.findByDistance("7.0")).thenReturn(filledList);
        ResponseEntity<?> responseEntity = search.searchByDistance("7.0");
        if (!(responseEntity.getStatusCodeValue() == 200)) {
            Assertions.fail();
        }
    }
    @Test
    void testSearchByDistanceFailed() {
        when(foodRepositoryMock.findByDistance("7.0")).thenReturn(emptyList);
        ResponseEntity<?> responseEntity = search.searchByDistance("7.0");
        if (!(responseEntity.getStatusCodeValue() == 400)) {
            Assertions.fail();
        }
    }


    @Test
    void testSearchByPrice() {
        when(foodRepositoryMock.findByPrice("my Home")).thenReturn(filledList);
        ResponseEntity<?> responseEntity = search.searchByprice("my Home");
        if (!(responseEntity.getStatusCodeValue() == 200)) {
            Assertions.fail();
        }
    }
    @Test
    void testSearchByPriceFailed() {
        when(foodRepositoryMock.findByPrice("my Home")).thenReturn(emptyList);
        ResponseEntity<?> responseEntity = search.searchByprice("my Home");
        if (!(responseEntity.getStatusCodeValue() == 400)) {
            Assertions.fail();
        }
    }


    @Test
    void testSearchByRestoName() {
        when(foodRepositoryMock.findByRestoName("my Home")).thenReturn(filledList);
        ResponseEntity<?> responseEntity = search.searchByRestroName("my Home");
        if (!(responseEntity.getStatusCodeValue() == 200)) {
            Assertions.fail();
        }
    }
    @Test
    void testSearchByRestoNameFailed() {
        when(foodRepositoryMock.findByRestoName("my Home")).thenReturn(emptyList);
        ResponseEntity<?> responseEntity = search.searchByRestroName("my Home");
        if (!(responseEntity.getStatusCodeValue() == 400)) {
            Assertions.fail();
        }
    }
}