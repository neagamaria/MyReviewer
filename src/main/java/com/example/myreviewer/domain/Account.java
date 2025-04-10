package com.example.myreviewer.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String role;
    private String description;

    @OneToMany(mappedBy="writer")
    private List<Article> articles;

    @OneToMany(mappedBy="reviewer")
    private List<Review> reviews;
}
