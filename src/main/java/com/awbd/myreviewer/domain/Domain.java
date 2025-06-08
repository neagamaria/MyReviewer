package com.awbd.myreviewer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    private String image;

    @ManyToMany
    @JoinTable(name = "article_domain",
            joinColumns =@JoinColumn(name="domain_id",referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name="article_id",referencedColumnName="id"))
    private List<Article> articles;
}
