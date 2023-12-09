package com.softuni.StudentClubs.mapper;

import com.softuni.StudentClubs.models.dto.ArticleDto;
import com.softuni.StudentClubs.models.entities.Article;
import org.modelmapper.ModelMapper;

public class ArticleMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Article mapToArticle(ArticleDto articleDto) {
        return modelMapper.map(articleDto, Article.class);
    }

    public static ArticleDto mapToArticleDto(Article article) {
        return modelMapper.map(article, ArticleDto.class);
    }
}
