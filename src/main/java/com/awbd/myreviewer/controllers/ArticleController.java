package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.domain.Level;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.dtos.DomainDTO;
import com.awbd.myreviewer.dtos.ReviewDTO;
import com.awbd.myreviewer.services.ArticleService;
import com.awbd.myreviewer.services.DomainService;
import com.awbd.myreviewer.services.ReviewService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    ArticleService articleService;
    DomainService domainService;
    ReviewService reviewService;


    public ArticleController(ArticleService articleService, DomainService domainService, ReviewService reviewService) {
        this.articleService = articleService;
        this.domainService = domainService;
        this.reviewService = reviewService;
    }

    @RequestMapping("")
    public String getPublicArticlesList(Model model) {
        List<ArticleDTO> articles = articleService.findAllPublic();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    // method to display document preview
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> previewDocument(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(System.getProperty("user.dir"), filename);

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource fileResource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileResource);
    }


    // form to add an article
    @GetMapping("/form")
    public String articleForm(Model model) {
        ArticleDTO article = new ArticleDTO();
        model.addAttribute("article", article);
        List<DomainDTO> allDomains = domainService.findAll();

        model.addAttribute("domains", allDomains);
        model.addAttribute("levels", Level.values());

        return "articleForm";
    }

    // save article
    @PostMapping("/save")
    public String saveArticle(@ModelAttribute("article") ArticleDTO article, @RequestParam("documentFile") MultipartFile file) {

        if(!file.isEmpty()) {
            articleService.saveWithDocument(article, file);
        }

        return "redirect:/articles";
    }

    // edit an article
    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable String id) {
        model.addAttribute("article",
                articleService.findById(Long.valueOf(id)));

        List<DomainDTO> categoriesAll = domainService.findAll();
        model.addAttribute("categoriesAll", categoriesAll );

        return "articleForm";
    }


    // delete article
    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        articleService.deleteById(Long.valueOf(id));
        return "redirect:/articles";
    }


    // get personal articles
    @RequestMapping("/user")
    public String getArticlesByUser(Model model) {
        List<ArticleDTO> articles = articleService.findByCurrentUser();
        model.addAttribute("articles", articles);
        return "myArticleList";
    }


    // form to add a review
    @GetMapping("/{articleId}/reviews/form")
    public String reviewForm(@PathVariable Long articleId, Model model) {
        ReviewDTO review = new ReviewDTO();
        model.addAttribute("articleId", articleId);
        model.addAttribute("review", review);

        return "reviewForm";
    }


    // add a review to article
    @PostMapping("/{articleId}/review")
    public String addReviewToArticle(@ModelAttribute ReviewDTO review, @PathVariable Long articleId) {
        ArticleDTO article = articleService.findById(articleId);
        reviewService.addReviewToArticle(review, article);

        return "redirect:/articles";
    }

    // get reviews for article
    @RequestMapping("/{articleId}/reviewList")
    public String getReviews(Model model, @PathVariable Long articleId) {
        List<ReviewDTO> reviews = reviewService.findAllByArticle(articleId);
        model.addAttribute("reviews", reviews);

        return "reviewList";
    }

    // change visibility
    @GetMapping("{articleId}/visibility")
    public String changeVisibility(@PathVariable Long articleId) {
        ArticleDTO article = articleService.findById(articleId);
        String currentVisibility = article.getVisibility();
        if(Objects.equals(currentVisibility, "public")) {
            article.setVisibility("private");
        }
        else {
            article.setVisibility("public");
        }

        return "myArticleList";
    }

    // find by domain
    @GetMapping("/domain/{domainId}")
    public String getByDomain(Model model, @PathVariable Long domainId) {
        List<ArticleDTO> articles = articleService.getByDomain(domainId);
        model.addAttribute("articles", articles);

        return "articleList";
    }
}
