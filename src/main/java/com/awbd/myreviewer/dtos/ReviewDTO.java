package com.awbd.myreviewer.dtos;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.domain.Article;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private long id;
    private String feedback;
    private Float grade;
    private LocalDateTime postedDate;
    private Account reviewer;
    private Article article;
}
