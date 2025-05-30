package com.awbd.myreviewer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must have valid format")
    private String email;
    @NotBlank(message = "Role is required")
    private String role;

    private String description;

    @OneToMany(mappedBy="writer", cascade = CascadeType.MERGE)
    private List<Article> articles;

    @OneToMany(mappedBy="reviewer")
    private List<Review> reviews;

}
