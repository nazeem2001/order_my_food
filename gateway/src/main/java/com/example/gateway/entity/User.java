package com.example.gateway.entity;

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
@Entity( name = "user")
public class User {
    @Id
    @Column(name = "userid")
    String userId ;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;
}
