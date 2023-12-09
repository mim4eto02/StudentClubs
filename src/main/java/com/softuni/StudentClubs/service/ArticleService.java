package com.softuni.StudentClubs.service;

import com.softuni.StudentClubs.models.dto.ArticleDto;
import com.softuni.StudentClubs.models.entities.Article;

import java.util.List;

public interface ArticleService {

    Article saveArticle(ArticleDto articleDto);

    List<ArticleDto> findAllArticles();

    ArticleDto findArticleById(long articleId);

    void updateArticle(ArticleDto articleDto);

    void deleteArticle(long articleId);

    List<ArticleDto> searchByTitle(String query);

    List<ArticleDto> findArticlesByAuthorId(Long id);
}
