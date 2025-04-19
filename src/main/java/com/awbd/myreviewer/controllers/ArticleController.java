package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.services.ArticleService;
import com.awbd.myreviewer.services.DomainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    ArticleService articleService;


    public ArticleController(ArticleService articleService, DomainService domainService) {
        this.articleService = articleService;
    }

    @RequestMapping("")
    public String articleList(Model model) {
        List<ArticleDTO> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "articleList";
    }
}
