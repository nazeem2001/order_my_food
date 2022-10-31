package com.example.orderservice.entity;
import java.math.BigDecimal;
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
@Document(collection = "orders")
public class Orders {
    @Id
    @Field("_id")
    String orderId;
    @Field("user_Id")
    String userId;
    @Field("items")
    HashSet<String> items;
    @Field("amount")
    BigDecimal amount;
}
