package com.example.teamOnebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createdDate;

    @Column(nullable = false)
    private String emotion; //good soso bad

    @Column(nullable = false)
    private String type; //happy worry

    private String content;

    public void testSetCreatedDate(LocalDate localDate)
    {
        this.createdDate = localDate;
    }
}
