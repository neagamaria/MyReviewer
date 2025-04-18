package com.awbd.myreviewer.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@DataJpaTest
@ActiveProfiles("h2")
public class EntityManagerTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    public void findArticle() {
        System.out.println(entityManager.getEntityManagerFactory());
        Article ArticleFound = entityManager.find(Article.class, 1L);
        assertEquals(1, ArticleFound.getId());
    }
}
