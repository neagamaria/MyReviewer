package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.domain.Domain;
import com.awbd.myreviewer.domain.Level;
import com.awbd.myreviewer.domain.Review;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.dtos.DomainDTO;
import com.awbd.myreviewer.dtos.ReviewDTO;
import com.awbd.myreviewer.exceptions.UnauthorizedException;
import com.awbd.myreviewer.services.ArticleService;
import com.awbd.myreviewer.services.DomainService;
import com.awbd.myreviewer.services.ReviewService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    ArticleService articleService;
    DomainService domainService;
    ReviewService reviewService;
    Logger logger;

    public ArticleController(ArticleService articleService, DomainService domainService, ReviewService reviewService) {
        this.articleService = articleService;
        this.domainService = domainService;
        this.reviewService = reviewService;
        this.logger = LoggerFactory.getLogger(ArticleController.class);
    }

//    @RequestMapping("")
//    public String getPublicArticlesList(Model model) {
//
//
//       List<ArticleDTO> articles = articleService.findAllPublic();
//       model.addAttribute("articles", articles);
//
//        return "articleList";
//    }


    @RequestMapping("")
    public String getWithPagination(Model model,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "postedDate") String sortBy,
                                        @RequestParam(defaultValue = "false") String ascendingString) {

        boolean ascending = Objects.equals(ascendingString, "true");
        // set sorting and paging
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Article> articles = articleService.findAllPublic(pageable);

        // calculate grades for star system
        List<Integer> stars = new ArrayList<Integer>();

        for(Article article: articles.getContent()) {
            List<ReviewDTO> reviews = reviewService.findAllByArticle(article.getId());

            Integer grade = 0;

            if(!reviews.isEmpty()) {
                for (ReviewDTO review : reviews) {
                    grade += review.getGrade().intValue();
                }

                grade = grade / reviews.size();
                stars.add(grade);
            }
            else {
                stars.add(0);
            }
        }

        model.addAttribute("stars", stars);

        boolean hasPrev = articles.hasPrevious();
        boolean hasNext = articles.hasNext();


        model.addAttribute("articles", articles);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("number", articles.getNumber());
        model.addAttribute("totalPages", articles.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("ascending", ascending);
        model.addAttribute("size", size);

        return "articleList";
    }

    // method to display document preview
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> previewDocument(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(System.getProperty("user.dir"), filename);

        if (!Files.exists(filePath)) {
            logger.error("LOG ERR: document not existent");
            return ResponseEntity.notFound().build();
        }

        Resource fileResource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileResource);
    }



    @GetMapping("/getDocument/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) throws IOException {
        ArticleDTO articleDTO = articleService.findById(id);

        if (articleDTO == null || articleDTO.getDocument() == null) {
            logger.error("LOG ERR: article or document not existent");
            return ResponseEntity.notFound().build();
        }

        String fileName = articleDTO.getDocument();
        Path filePath = Paths.get(fileName);

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileBytes = Files.readAllBytes(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }



    // form to add an article
    @GetMapping("/form")
    public String articleForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities().iterator().next().getAuthority());
        if(Objects.equals(auth.getAuthorities().iterator().next().getAuthority(), "ROLE_REVIEWER")) {
            throw new UnauthorizedException("Missing authorizations.");
        }

        ArticleDTO article = new ArticleDTO();
        model.addAttribute("article", article);
        List<DomainDTO> allDomains = domainService.findAll();

        model.addAttribute("domainsAll", allDomains);
        model.addAttribute("levels", Level.values());

        return "articleForm";
    }

    // save article
    @PostMapping("/save")
    public String saveArticle(@Valid @ModelAttribute("article") ArticleDTO article, BindingResult bindingResult,
                              @RequestParam("documentFile") MultipartFile file, Model model) {

        if (bindingResult.hasErrors()) {

            List<DomainDTO> allDomains = domainService.findAll();
            model.addAttribute("domainsAll", allDomains);
            model.addAttribute("levels", Level.values());

            return "articleForm";
        }

        if(file.isEmpty()) {
            bindingResult.rejectValue("document", "NotBlank", "Document is required");
        }
       else {
            articleService.saveWithDocument(article, file);
        }

        return "redirect:/articles";
    }

    // edit an article
    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable String id) {
        ArticleDTO article = articleService.findById(Long.valueOf(id));

        if(article == null) {
            logger.warn("LOG ERR: article not existent");
            return "notFoundException";
        }
        else {
            model.addAttribute("article", article);

              List<DomainDTO> domains = domainService.findAll();
              System.out.println(domains);
              model.addAttribute("domainsAll", domains);
              model.addAttribute("levels", Level.values());

            return "articleForm";
        }
    }


    // delete article
    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        List<ReviewDTO> reviews = reviewService.findAllByArticle(Long.valueOf(id));
        if(!reviews.isEmpty()) {
            for(ReviewDTO review: reviews) {
                reviewService.deleteById(review.getId());
            }
        }

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities().iterator().next().getAuthority());
        if(Objects.equals(auth.getAuthorities().iterator().next().getAuthority(), "ROLE_WRITER")) {
            throw new UnauthorizedException("Missing authorizations.");
        }

        ReviewDTO review = new ReviewDTO();
        model.addAttribute("articleId", articleId);
        model.addAttribute("review", review);

        return "reviewForm";
    }


    // add a review to article
    @PostMapping("/{articleId}/review")
    public String addReviewToArticle(@Valid @ModelAttribute("review") ReviewDTO review, BindingResult bindingResult, @PathVariable Long articleId, Model model) {

        if(bindingResult.hasErrors()) {
            logger.warn("LOG WARN: wrong form completion");
            model.addAttribute("review", review);
            model.addAttribute("articleId", articleId);
            return "reviewForm";
        }
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
            System.out.println(article.getVisibility());
        }
        else {
            article.setVisibility("public");
        }

        return "myArticleList";
    }


    // find by domain
    @GetMapping("/domain/{domainId}")
    public String getByDomain(@PathVariable Long domainId, Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "3") int size,
                                @RequestParam(defaultValue = "postedDate") String sortBy,
                                @RequestParam(defaultValue = "false") String ascendingString) {

        boolean ascending = Objects.equals(ascendingString, "true");
        // set sorting and paging
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Article> articles = articleService.getByDomain(domainId, pageable);

        List<Integer> stars = new ArrayList<Integer>();

        for(Article article: articles.getContent()) {
            List<ReviewDTO> reviews = reviewService.findAllByArticle(article.getId());

            Integer grade = 0;

            if(!reviews.isEmpty()) {
                for (ReviewDTO review : reviews) {
                    grade += review.getGrade().intValue();
                }

                grade = grade / reviews.size();
                stars.add(grade);
            }
            else {
                stars.add(0);
            }
        }

        model.addAttribute("stars", stars);

        boolean hasPrev = articles.hasPrevious();
        boolean hasNext = articles.hasNext();


        model.addAttribute("articles", articles);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("number", articles.getNumber());
        model.addAttribute("totalPages", articles.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("ascending", ascending);
        model.addAttribute("size", size);

        return "articleList";
    }
}
