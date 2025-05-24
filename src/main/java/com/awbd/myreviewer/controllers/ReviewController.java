package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.dtos.DomainDTO;
import com.awbd.myreviewer.services.ArticleService;
import com.awbd.myreviewer.services.DomainService;
import com.awbd.myreviewer.services.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    ArticleService articleService;
    ReviewService reviewService;

    public ReviewController(ArticleService articleService, ReviewService reviewService) {
        this.articleService = articleService;
        this.reviewService = reviewService;
    }

    // delete article
    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        reviewService.deleteById(Long.valueOf(id));
        return "redirect:/articles";
    }

}
