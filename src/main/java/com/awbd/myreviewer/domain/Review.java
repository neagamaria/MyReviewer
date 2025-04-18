package com.awbd.myreviewer.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String feedback;
    private Float grade;
    private LocalDateTime postedDate;
    @ManyToOne
    private Account reviewer;
    @ManyToOne
    private Article article;
}
