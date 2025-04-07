package com.example.myreviewer.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.w3c.dom.Text;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalDateTime postedDate;
    private String description;
    private String visibility;

    // document uploaded for review
    private String document;

    @ManyToOne
    private User writer;

    @OneToMany(mappedBy="article")
    private List<Review> reviews;

    @ManyToMany(mappedBy="articles")
    private List<Domain> domains;

}
