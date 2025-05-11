package com.awbd.myreviewer.repositories.security;

import com.awbd.myreviewer.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}