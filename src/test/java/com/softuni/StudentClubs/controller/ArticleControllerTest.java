package com.softuni.StudentClubs.controller;
import com.softuni.StudentClubs.models.dto.ArticleDto;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.ArticleService;
import com.softuni.StudentClubs.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ArticleController articleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    void listArticles() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(articleService.findAllArticles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles-list"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(articleService, times(1)).findAllArticles();
    }

    @Test
    void createArticleForm() throws Exception {
        mockMvc.perform(get("/articles/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles-create"))
                .andExpect(model().attributeExists("article"));

    }

    @Test
    void editArticleForm() throws Exception {
        long articleId = 1L;
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(articleService.findArticleById(articleId)).thenReturn(new ArticleDto());

        mockMvc.perform(get("/articles/{articleId}/edit", articleId))
                .andExpect(status().isOk())
                .andExpect(view().name("articles-edit"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(articleService, times(1)).findArticleById(articleId);
    }

    @Test
    void deleteArticle() throws Exception {
        long articleId = 1L;

        mockMvc.perform(get("/articles/{articleId}/delete", articleId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));

        verify(articleService, times(1)).deleteArticle(articleId);
    }

    @Test
    void searchArticles() throws Exception {
        when(articleService.searchByTitle(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/articles/search").param("query", "article"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles-list"))
                .andExpect(model().attributeExists("articles"));

        verify(articleService, times(1)).searchByTitle("article");
    }

    @Test
    void articleDetail() throws Exception {
        long articleId = 1L;
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(articleService.findArticleById(articleId)).thenReturn(new ArticleDto());

        mockMvc.perform(get("/articles/{articleId}", articleId))
                .andExpect(status().isOk())
                .andExpect(view().name("articles-detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(articleService, times(1)).findArticleById(articleId);
    }

}
