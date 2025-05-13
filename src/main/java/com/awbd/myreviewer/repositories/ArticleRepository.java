package com.awbd.myreviewer.repositories;

import com.awbd.myreviewer.domain.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// interface for Article CRUD operations
public interface ArticleRepository extends CrudRepository<Article, Long> {
    Optional<Article> findById(Long id);
    Optional<Article> findByName(String name);

    @Query("select a from Article a where a.visibility = 'public'")
    List<Article> getALLPublic();
    @Query("select a from Article a where a.writer.name = :name")
    List<Article> findByWriterName(@Param("name") String writerName);

    @Query("select a from Article a where a.writer.id = ?1")
    List<Article> findByWriter(Long writerId);

    Article save(Article article);
}
