package com.example.orderservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Food {
    @Id
    String id;
    String name;
    String description;
    @Column(name = "resto_name")
    String restoName;
    String distance;
    String price;
    String cusine;
    String location;


}
