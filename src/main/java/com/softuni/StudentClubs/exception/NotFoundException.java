package com.softuni.StudentClubs.exception;

public class NotFoundException extends RuntimeException{

    private final Long id;
    private static final String DEFAULT_MESSAGE = "Not found!";

    public NotFoundException(Long id) {
        super(DEFAULT_MESSAGE);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
