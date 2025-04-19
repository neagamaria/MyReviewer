package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.repositories.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    ArticleRepository articleRepository;
    ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ArticleDTO> findAll() {
        List<Article> articles = new LinkedList<>();
        articleRepository.findAll().iterator().forEachRemaining(articles::add);

        return articles.stream()
                .map(product -> modelMapper.map(product, ArticleDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public ArticleDTO findById(Long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);

        if(articleOptional.isEmpty()) {
            throw new RuntimeException("Article not found!");
        }

        return modelMapper.map(articleOptional.get(), ArticleDTO.class);
    }

    @Override
    public ArticleDTO save(ArticleDTO article) {

        Article savedArticle = articleRepository.save(modelMapper.map(article, Article.class));
        return modelMapper.map(savedArticle, ArticleDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

}
