package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.models.dto.ArticleDto;
import com.softuni.StudentClubs.service.ArticleService;
import com.softuni.StudentClubs.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(ArticleController.class)
@RunWith(SpringRunner.class)
public class ArticleControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private UserService userService;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

//    @Test
//    public void listArticles_ShouldReturnArticlesListPage() throws Exception {
//        List<ArticleDto> articles = new ArrayList<>();
//        when(articleService.findAllArticles()).thenReturn(articles);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/articles"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("articles-list"))
//                .andExpect(MockMvcResultMatchers.model().attribute("articles", articles));
//    }
//
//
//
//    @Test
//    public void createArticle_ShouldSaveArticleAndRedirectToListPage() throws Exception {
//        ArticleDto articleDto = new ArticleDto();
//        articleDto.setTitle("Test Article");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/articles/new")
//                        .flashAttr("article", articleDto))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/articles"));
//
//        verify(articleService, times(1)).saveArticle(articleDto);
//    }
}
