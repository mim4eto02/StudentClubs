package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.exception.ArticleNotFoundException;
import com.softuni.StudentClubs.models.dto.ArticleDto;
import com.softuni.StudentClubs.models.entities.Article;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.repository.ArticleRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.EmailService;
import com.softuni.StudentClubs.service.impl.ArticleServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void saveArticle() {
//        // Arrange
//        ArticleDto articleDto = new ArticleDto();
//        articleDto.setTitle("Test Article");
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername("testUser");
//
//        when(userRepository.findByUsername(any())).thenReturn(userEntity);
//        when(articleRepository.save(any())).thenReturn(new Article());
//
//        // Act
//        Article savedArticle = articleService.saveArticle(articleDto);
//
//        // Assert
//        assertNotNull(savedArticle);
//        assertEquals("testUser", savedArticle.getAuthor().getUsername());
//        verify(userRepository, times(1)).findByUsername(any());
//        verify(articleRepository, times(1)).save(any());
//    }

    @Test
    public void findAllArticles() {
        when(articleRepository.findAll()).thenReturn(Arrays.asList(new Article(), new Article()));

        List<ArticleDto> articles = articleService.findAllArticles();

        assertNotNull(articles);
        assertEquals(2, articles.size());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    public void findArticleById() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(new Article()));

        ArticleDto articleDto = articleService.findArticleById(1L);

        assertNotNull(articleDto);
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    public void updateArticle() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle("Updated Article");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        when(userRepository.findByUsername(any())).thenReturn(userEntity);
        when(articleRepository.save(any())).thenReturn(new Article());

        articleService.updateArticle(articleDto);

        verify(userRepository, times(1)).findByUsername(any());
        verify(articleRepository, times(1)).save(any());
    }

    @Test
    public void deleteArticleArticleNotFound() {
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ArticleNotFoundException.class, () -> articleService.deleteArticle(1L));
        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, never()).deleteById(anyLong());
        verify(emailService, never()).sendDeletionEmail(any(), any(), any());
    }

    @Test
    public void searchByTitle() {
        when(articleRepository.findByTitleContaining("Test")).thenReturn(Arrays.asList(new Article(), new Article()));

        List<ArticleDto> articles = articleService.searchByTitle("Test");

        assertNotNull(articles);
        assertEquals(2, articles.size());
        verify(articleRepository, times(1)).findByTitleContaining("Test");
    }

    @Test
    public void findArticlesByAuthorId() {
        when(articleRepository.findArticlesByAuthor_Id(1L)).thenReturn(Arrays.asList(new Article(), new Article()));

        List<ArticleDto> articles = articleService.findArticlesByAuthorId(1L);

        assertNotNull(articles);
        assertEquals(2, articles.size());
        verify(articleRepository, times(1)).findArticlesByAuthor_Id(1L);
    }
}