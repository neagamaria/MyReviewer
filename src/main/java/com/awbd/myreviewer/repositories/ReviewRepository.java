package com.awbd.myreviewer.repositories;

import com.awbd.myreviewer.domain.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// interface for Review CRUD operations
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("select r from Review r where r.article.id = ?1")
    List<Review> findByArticle(Long articleId);

//    @Query("select r from Review r where r.reviewer.id = ?1")
//    List<Review> findByReviewer(Long reviewerId);

    Review save(Review review);
}
