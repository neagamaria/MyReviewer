package com.example.myreviewer.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String feedback;
    private Integer grade;
    @ManyToOne
    private User reviewer;
    @ManyToOne
    private Article article;
}
