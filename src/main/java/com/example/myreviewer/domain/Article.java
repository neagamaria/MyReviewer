package com.example.myreviewer.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Date postedDate;
    private String description;
    private String visibility;

    // document uploaded for review
    private String document;

    @ManyToOne
    private Account writer;

    @OneToMany(mappedBy="article")
    private List<Review> reviews;

    @ManyToMany(mappedBy="articles")
    private List<Domain> domains;

    @Enumerated(value = EnumType.STRING)
    private Level level;

}
