package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.exception.ArticleNotFoundException;
import com.softuni.StudentClubs.mapper.ArticleMapper;
import com.softuni.StudentClubs.models.dto.ArticleDto;
import com.softuni.StudentClubs.models.entities.Article;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.repository.ArticleRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements com.softuni.StudentClubs.service.ArticleService {

    private final com.softuni.StudentClubs.repository.ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, EmailService emailService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }


    @Override
    public Article saveArticle(ArticleDto articleDto) {
        String username = com.softuni.StudentClubs.security.SecurityUtil.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new RuntimeException("User not found!");
        }
        if (articleDto.getAuthor() == null) {
            articleDto.setAuthor(userEntity);
        }
        Article article = ArticleMapper.mapToArticle(articleDto);
        return articleRepository.save(article);
    }

    @Override
    public List<ArticleDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(ArticleMapper::mapToArticleDto).collect(Collectors.toList());
    }

    @Override
    public ArticleDto findArticleById(long articleId) {
       return articleRepository.findById(articleId)
               .map(ArticleMapper::mapToArticleDto)
               .orElse(null);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) {
        String username = com.softuni.StudentClubs.security.SecurityUtil.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        Article article = ArticleMapper.mapToArticle(articleDto);
        article.setAuthor(userEntity);
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Article> article = articleRepository.findById(articleId);

        if (article.isPresent()) {
            Article articleToDelete = article.get();
            String articleName= articleToDelete.getTitle();
            String email = articleToDelete.getAuthor().getEmail();
            articleRepository.deleteById(articleId);

            if(authentication.getAuthorities().stream().anyMatch(
                    grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
                    emailService.sendDeletionEmail("Article", articleName, email);
            }
        } else {
            throw new ArticleNotFoundException(articleId);
        }
    }

    @Override
    public List<ArticleDto> searchByTitle(String query) {
        List<Article> articles = articleRepository.findByTitleContaining(query);
        return articles.stream().map(ArticleMapper::mapToArticleDto).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findArticlesByAuthorId(Long id) {
        List<Article> articles = articleRepository.findArticlesByAuthor_Id(id);
        return articles.stream().map(ArticleMapper::mapToArticleDto).collect(Collectors.toList());
    }
}
