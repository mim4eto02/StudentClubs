package com.softuni.StudentClubs.service;

public interface EmailService {

    void sendRegistrationEmail(String email, String username);

    void sendContactEmail(String name, String email, String subject, String message);

}
