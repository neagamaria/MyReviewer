package com.awbd.myreviewer.domain;

import com.awbd.myreviewer.repositories.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
public class ArticleRepositoryTest {

    ArticleRepository articleRepository;

    @Autowired
    ArticleRepositoryTest(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Test
    public void findArticles() {
        List<Article> articles = articleRepository.findByWriter(1L);
        assertTrue(articles.size() >= 1);
        log.info("findByWriter...");
        articles.forEach(article -> log.info(article.getName()));
    }

    @Test
    public void findArticlesByWriterName() {
        List<Article> articles = articleRepository.findByWriterName("User1");
        assertTrue(articles.size() >= 1);
        log.info("findByWriter ByWriterName...");
        articles.forEach(article -> log.info(article.getName()));
    }
}
