package com.example.teamOnebe.entity;

import jakarta.persistence.*;

@Entity
public class Owns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;




}
