package com.awbd.myreviewer.dtos;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.domain.Article;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Feedback is required")
    private String feedback;
    @NotNull(message = "Grade is required")
    @Min(value=1, message="Grade must be between 1 and 5")
    @Max(value=5, message="Grade must be between 1 and 5")
    private Float grade;
    private LocalDateTime postedDate;
    private Account reviewer;
    private Article article;
}
