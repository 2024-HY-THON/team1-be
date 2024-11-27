package com.example.teamOnebe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Tree {

    @ManyToOne
    private User user;
}
