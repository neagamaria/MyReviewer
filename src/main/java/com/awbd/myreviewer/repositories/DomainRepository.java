package com.awbd.myreviewer.repositories;

import com.awbd.myreviewer.domain.Domain;
import org.springframework.data.repository.CrudRepository;

// interface for Domain CRUD operations
public interface DomainRepository extends CrudRepository<Domain, Long> {
}
