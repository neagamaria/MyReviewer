package com.awbd.myreviewer.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @OneToMany(mappedBy="writer", cascade = CascadeType.MERGE)
    private List<Article> articles;

    @OneToMany(mappedBy="reviewer")
    private List<Review> reviews;

}
