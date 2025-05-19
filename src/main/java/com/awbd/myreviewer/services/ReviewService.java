package com.awbd.myreviewer.services;

import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.dtos.ReviewDTO;

import java.util.List;

public interface ReviewService {
    public List<ReviewDTO> findAllByArticle(Long articleId);

    public void addReviewToArticle(ReviewDTO reviewDTO, ArticleDTO articleDTO);
}
