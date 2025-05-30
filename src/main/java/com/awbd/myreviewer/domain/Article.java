package com.awbd.myreviewer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Name is required")
    private String name;
    private Date postedDate;
    private String description;
    @NotBlank(message = "Visibility is required")
    private String visibility;

    // document uploaded for review
    @NotBlank(message = "Document is required")
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
