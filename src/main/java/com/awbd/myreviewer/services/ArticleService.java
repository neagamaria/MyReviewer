package com.awbd.myreviewer.services;

import com.awbd.myreviewer.dtos.ArticleDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> findAll();
    List<ArticleDTO> findAllPublic();
    ArticleDTO findById(Long id);
    ArticleDTO save(ArticleDTO article);
    void deleteById(Long id);
    void uploadDocument(ArticleDTO article, MultipartFile file);
}
