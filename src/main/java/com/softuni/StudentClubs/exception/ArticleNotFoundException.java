package com.softuni.StudentClubs.exception;

public class ArticleNotFoundException extends RuntimeException {

    private final Long id;
    private static final String DEFAULT_MESSAGE = "Article not found!";

    public ArticleNotFoundException(Long id) {
        super(DEFAULT_MESSAGE);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
