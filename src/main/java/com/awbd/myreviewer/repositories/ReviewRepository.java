package com.awbd.myreviewer.repositories;

import com.awbd.myreviewer.domain.Review;
import org.springframework.data.repository.CrudRepository;

// interface for Review CRUD operations
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
