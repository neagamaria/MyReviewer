package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.domain.Domain;
import com.awbd.myreviewer.domain.Level;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.dtos.DomainDTO;
import com.awbd.myreviewer.services.ArticleService;
import com.awbd.myreviewer.services.DomainService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    ArticleService articleService;
    DomainService domainService;


    public ArticleController(ArticleService articleService, DomainService domainService) {
        this.articleService = articleService;
        this.domainService = domainService;
    }

    @RequestMapping("")
    public String getPublicArticlesList(Model model) {
        List<ArticleDTO> articles = articleService.findAllPublic();
        model.addAttribute("articles", articles);
        return "articleList";
    }


    // form to add an article
    @RequestMapping("/form")
    public String articleForm(Model model) {
        ArticleDTO article = new ArticleDTO();
        model.addAttribute("article", article);
        List<DomainDTO> allDomains = domainService.findAll();

        model.addAttribute("domains", allDomains);
        model.addAttribute("levels", Level.values());

        return "articleForm";
    }

    // save method
    @PostMapping("/articles")
    public String saveArticle(@ModelAttribute ArticleDTO article, @RequestParam("document") MultipartFile file,
                              @RequestParam("domains") List<Long> domainIds) {
        List<DomainDTO> domains = domainService.findByIds(domainIds);


      //  article.setDomains(domains);

        if(!file.isEmpty()) {
            articleService.uploadDocument(article, file);
        }

        articleService.save(article);

        return "redirect:/articles";
    }

}
