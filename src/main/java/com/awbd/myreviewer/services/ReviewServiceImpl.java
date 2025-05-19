package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.domain.Review;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.dtos.ReviewDTO;
import com.awbd.myreviewer.repositories.AccountRepository;
import com.awbd.myreviewer.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{
    ReviewRepository reviewRepository;
    AccountRepository accountRepository;
    ModelMapper modelMapper;
    public ReviewServiceImpl(ReviewRepository reviewRepository, AccountRepository accountRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }
    public List<ReviewDTO> findAllByArticle(Long articleId) {
        List<Review> reviews = new LinkedList<>();
        reviewRepository.findByArticle(articleId).iterator().forEachRemaining(reviews::add);

        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    public void addReviewToArticle(ReviewDTO reviewDTO, ArticleDTO articleDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        Article article = modelMapper.map(articleDTO, Article.class);

        // set the current logged-in user as reviewer
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Account reviewer = accountRepository.findByName(user);
        review.setReviewer(reviewer);

        // set the posted date
        review.setPostedDate(LocalDateTime.now());

        // set article
        review.setArticle(article);

        reviewRepository.save(review);
    }
}
