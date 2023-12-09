package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.models.dto.ArticleDto;
import com.softuni.StudentClubs.models.entities.Article;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.ArticleService;
import com.softuni.StudentClubs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    private final UserService userService;

    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("/articles")
    public String listArticles(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        List<ArticleDto> articles = articleService.findAllArticles();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("articles", articles);
        return "articles-list";
    }

    @GetMapping("/articles/new")
    public String createArticleForm(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        return "articles-create";
    }

    @GetMapping("/articles/{articleId}/edit")
    public String editArticleForm(@PathVariable("articleId") long articleId, Model model) {
        ArticleDto article = articleService.findArticleById(articleId);
        UserEntity user= new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("article", article);
        return "articles-edit";
    }

    @GetMapping("/articles/{articleId}/delete")
    public String deleteArticle(@PathVariable("articleId") long articleId) {
        articleService.deleteArticle(articleId);
        return "redirect:/articles";
    }

    @GetMapping("/articles/search")
    public String searchArticles(@ModelAttribute("query") String query, Model model) {
        List<ArticleDto> articles = articleService.searchByTitle(query);
        model.addAttribute("articles", articles);
        return "articles-list";
    }

    @GetMapping("/articles/{articleId}")
    public String articleDetail(@PathVariable("articleId") long articleId, Model model) {
        UserEntity user;
        ArticleDto article = articleService.findArticleById(articleId);
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("article", article);
        return "articles-detail";
    }

    @PostMapping("articles/new")
    public String createArticle(@Valid @ModelAttribute("article") ArticleDto article, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("article", article);
            return "articles-create";
        }
        articleService.saveArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles/myarticles")
    public String myArticles(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        List<ArticleDto> articles = articleService.findArticlesByAuthorId(user.getId());
        model.addAttribute("articles", articles);
        return "articles-list";
    }
    @PostMapping("/articles/{articleId}/edit")
    public String updateArticle(@PathVariable("articleId") long articleId, @Valid @ModelAttribute ArticleDto article, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("article", article);
            return "articles-edit";
        }
        article.setId(articleId);
        articleService.updateArticle(article);
        return "redirect:/articles";
    }
}
