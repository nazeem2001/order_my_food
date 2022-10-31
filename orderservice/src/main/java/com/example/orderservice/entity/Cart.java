package com.example.orderservice.entity;

import java.util.HashSet;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "cart")
public class Cart {
    @Id
    @Field("_id")
    String userId;
    @Field("items")
    HashSet<String>items;

    public Cart(String userId){
        this.userId=userId;
        this.items = new HashSet<>();
    }
}
