package com.awbd.myreviewer.repositories;

import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.domain.Domain;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

// interface for Domain CRUD operations
public interface DomainRepository extends CrudRepository<Domain, Long> {
    Optional<Domain> findByName(String name);

    List<Domain> findByIdIn(List<Long> id);
}
