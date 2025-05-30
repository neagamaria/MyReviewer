package com.awbd.myreviewer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Feedback is required")
    private String feedback;
    @NotNull(message = "Grade is required")
    @Min(value=1, message="Grade must be between 1 and 5")
    @Max(value=5, message="Grade must be between 1 and 5")
    private Float grade;
    private LocalDateTime postedDate;
    @ManyToOne
    private Account reviewer;
    @ManyToOne
    private Article article;
}
