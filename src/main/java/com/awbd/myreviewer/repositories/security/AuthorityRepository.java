package com.awbd.myreviewer.repositories.security;

import com.awbd.myreviewer.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    @Query("select a from Authority a where a.role = :role")
    Optional<Authority> findByRole(@Param("role") String role);
}