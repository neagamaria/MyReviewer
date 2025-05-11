package com.awbd.myreviewer.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;

    @ManyToMany
    @JoinTable(name = "article_domain",
            joinColumns =@JoinColumn(name="domain_id",referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name="article_id",referencedColumnName="id"))
    private List<Article> articles;
}
