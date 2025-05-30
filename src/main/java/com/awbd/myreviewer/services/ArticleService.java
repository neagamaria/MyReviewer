package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.dtos.ArticleDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> findAll();
    public Page<Article> findAll(Pageable pageable);
    List<ArticleDTO> findAllPublic();
    ArticleDTO findById(Long id);
    List<ArticleDTO> findByCurrentUser();
    List<ArticleDTO> getByDomain(Long domainId);
    void deleteById(Long id);
    void saveWithDocument(ArticleDTO article, MultipartFile file);
}
