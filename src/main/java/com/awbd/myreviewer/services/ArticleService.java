package com.awbd.myreviewer.services;

import com.awbd.myreviewer.dtos.ArticleDTO;

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> findAll();
    ArticleDTO findById(Long id);
    ArticleDTO save(ArticleDTO article);
    void deleteById(Long id);
}
