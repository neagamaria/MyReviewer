package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Article;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.repositories.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> findAllPublic() {
        Optional<Article> publicArticles = articleRepository.getALLPublic();

        if(publicArticles.isEmpty()) {
            throw new RuntimeException("Article not found!");
        }
        return publicArticles.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
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


    @Override
    public void uploadDocument(ArticleDTO articleDTO, MultipartFile file) {
        Article article = modelMapper.map(articleDTO, Article.class);
        String uploadPath = "";

        try {
            if(!file.isEmpty()) {
                String fileName = UUID.randomUUID() + file.getOriginalFilename();
                Path filePath = Paths.get(uploadPath, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                article.setDocument(fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
