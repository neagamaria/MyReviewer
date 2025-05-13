package com.awbd.myreviewer.repositories;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.domain.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

// interface for Account CRUD operations
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByNameLike(String name);
    List<Account> findByIdIn(List<Long> ids);

    Account findByName(String name);
}
