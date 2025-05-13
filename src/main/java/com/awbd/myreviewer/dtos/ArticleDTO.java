package com.awbd.myreviewer.dtos;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.domain.Domain;
import com.awbd.myreviewer.domain.Level;
import com.awbd.myreviewer.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private Long id;
    private String name;
    private Date postingDate;
    private Date postedDate;
    private String description;
    private String visibility;
//    private String document;
    private Account writer;
    private List<Review> reviews;
    private List<Domain> domains;
    private Level level;
}
