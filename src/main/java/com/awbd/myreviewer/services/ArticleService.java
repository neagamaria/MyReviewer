package com.awbd.myreviewer.services;

import com.awbd.myreviewer.dtos.ArticleDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> findAll();
    List<ArticleDTO> findAllPublic();
    ArticleDTO findById(Long id);
    List<ArticleDTO> findByCurrentUser();
    void deleteById(Long id);
    void saveWithDocument(ArticleDTO article, MultipartFile file);
}
