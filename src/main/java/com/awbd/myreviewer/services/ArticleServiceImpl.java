package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.domain.Domain;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.dtos.DomainDTO;
import com.awbd.myreviewer.exceptions.ResourceNotFoundException;
import com.awbd.myreviewer.repositories.AccountRepository;
import com.awbd.myreviewer.repositories.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    ArticleRepository articleRepository;
    AccountRepository accountRepository;
    ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, AccountRepository accountRepository,
                              ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ArticleDTO> findAll() {
        List<Article> articles = new LinkedList<>();
        articleRepository.findAll().iterator().forEachRemaining(articles::add);

        return articles.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
       return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> findAllPublic(Pageable pageable) {
        Page<Article> publicArticles = articleRepository.getALLPublic(pageable);

        if (publicArticles.isEmpty()) {
            throw new RuntimeException("Article not found!");
        }
        return publicArticles;
    }


    @Override
    public ArticleDTO findById(Long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isEmpty()) {
            throw new ResourceNotFoundException("article " + id + " not found");
        }

        return modelMapper.map(articleOptional.get(), ArticleDTO.class);
    }

    @Override
    public void deleteById(Long id) {

        Optional<Article> optionalArticle = articleRepository.findById(id);

        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();

            // Remove this article from each associated domain
            if (article.getDomains() != null) {
                for (Domain domain : article.getDomains()) {
                    domain.getArticles().remove(article);
                }
                article.getDomains().clear(); // Remove all domain associations from article
            }

            articleRepository.save(article); // Persist the relationship removal
            articleRepository.deleteById(id);
        }
    }


    @Override
    public void saveWithDocument(ArticleDTO articleDTO, MultipartFile file) {
        Article article = modelMapper.map(articleDTO, Article.class);
        String uploadPath = "";

        try {
            if (!file.isEmpty()) {
                // set document
                String fileName = UUID.randomUUID() + file.getOriginalFilename();
                Path filePath = Paths.get(uploadPath, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                article.setDocument(fileName);

               //  set account to which the article is assigned
                String user = SecurityContextHolder.getContext().getAuthentication().getName();

                Optional<Account> writer = accountRepository.findByName(user);

                writer.ifPresent(article::setWriter);


                // set the current date
                article.setPostedDate(new Date());

                // save the link to domains
                List<Domain> domains = article.getDomains();
                if(!domains.isEmpty()) {
                    for(Domain domain: domains) {
                        domain.getArticles().add(article);
                    }
                }

                articleRepository.save(article);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<ArticleDTO> findByCurrentUser() {
        List<Article> articles = new LinkedList<>();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        articleRepository.findByWriterName(user).iterator().forEachRemaining(articles::add);

        return articles.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Article> getByDomain(Long domainId, Pageable pageable) {
        return articleRepository.findByDomain(domainId, pageable);
    }
}


