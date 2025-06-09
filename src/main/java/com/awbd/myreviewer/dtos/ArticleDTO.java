package com.awbd.myreviewer.dtos;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.domain.Domain;
import com.awbd.myreviewer.domain.Level;
import com.awbd.myreviewer.domain.Review;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
public class ArticleDTO {

    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    private Date postedDate;
    private String description;
    @NotBlank(message = "Visibility is required")
    private String visibility;
//    private String document;
    private Account writer;
//    @NotBlank(message = "Document is required")
    private String document;
    private List<Review> reviews;
    private List<Domain> domains;
    private Level level;
}
