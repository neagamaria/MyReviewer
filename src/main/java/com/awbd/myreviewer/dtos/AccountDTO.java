package com.awbd.myreviewer.dtos;

import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AccountDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private List<Article> articles;
    private List<Review> reviews;
}
