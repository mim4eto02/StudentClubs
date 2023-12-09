package com.softuni.StudentClubs.repository;

import com.softuni.StudentClubs.models.dto.ArticleDto;
import com.softuni.StudentClubs.models.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE lower(a.title) LIKE lower(CONCAT('%',:query,'%'))")
    List<Article> findByTitleContaining(String query);

    @Query("SELECT a FROM Article a WHERE a.author.id = :id")
    List<Article> findArticlesByAuthor_Id(Long id);
}
