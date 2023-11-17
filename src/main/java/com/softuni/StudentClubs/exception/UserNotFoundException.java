package com.softuni.StudentClubs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final Long id;
    private static final String DEFAULT_MESSAGE = "User not found!";

    public UserNotFoundException(Long id) {
        super(DEFAULT_MESSAGE);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
