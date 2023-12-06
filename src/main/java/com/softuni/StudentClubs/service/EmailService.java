package com.softuni.StudentClubs.service;

public interface EmailService {

    void sendRegistrationEmail(String email, String username);

    void sendContactEmail(String name, String email, String subject, String message);

    void sendDeactivationEmail(String email, String username);

    void sendActivationEmail(String email, String username);

    void sendEmailWithAttachment(String email, String subject, String body, byte[] calendarAttachment, String s);
}
