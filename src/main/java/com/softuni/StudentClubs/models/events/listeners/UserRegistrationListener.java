package com.softuni.StudentClubs.models.events.listeners;

import com.softuni.StudentClubs.models.events.UserRegistrationEvent;
import com.softuni.StudentClubs.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {

    private final EmailService emailService;

    public UserRegistrationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleUserRegistrationEvent(UserRegistrationEvent userRegistrationEvent) {
        String email = userRegistrationEvent.getEmail();
        String username = userRegistrationEvent.getUsername();

        emailService.sendRegistrationEmail(email, username);
    }
}
