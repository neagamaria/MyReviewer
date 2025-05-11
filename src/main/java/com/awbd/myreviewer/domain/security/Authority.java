package com.awbd.myreviewer.domain.security;

import jakarta.persistence.*;
import com.awbd.myreviewer.domain.security.User;
import lombok.Builder;
import lombok.*;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}
