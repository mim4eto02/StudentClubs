package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.service.EmailService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendRegistrationEmail(String email, String username) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);


        try {
            mimeMessageHelper.setFrom("student_clubs@example.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Welcome to Student Clubs!");
            mimeMessageHelper.setText(generateMessageContentRegistration(username), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String generateMessageContentRegistration(String username) {
        Context context = new Context();
        context.setVariable("username", username);
        return templateEngine.process("email/registration", context);
    }

    @Override
    public void sendContactEmail(String name, String email, String subject, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(email);
            mimeMessageHelper.setTo("student_clubs@example.com");
            mimeMessageHelper.setSubject(subject);

            String sb = "Name: " + name + System.lineSeparator() +
                    "Email: " + email + System.lineSeparator() +
                    "Message: " + message + System.lineSeparator();

            mimeMessageHelper.setText(sb);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendDeactivationEmail(String email, String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("student_club@example.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Account deactivated");
            mimeMessageHelper.setText(generateMessageContentDeactivation(username), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);

    }
}

    @Override
    public void sendActivationEmail(String email, String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("student_clubs@example.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Account activated");
            mimeMessageHelper.setText(generateMessageContentActivation(username), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithAttachment(String email, String subject, String body, byte[] calendarAttachment, String s) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("student-clubs@example.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.addAttachment(s, new ByteArrayResource(calendarAttachment));

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private String generateMessageContentActivation(String username) {
        Context context = new Context();
        context.setVariable("username", username);
        return templateEngine.process("email/activation", context);
    }

    private String generateMessageContentDeactivation(String username) {
        Context context = new Context();
        context.setVariable("username", username);
        return templateEngine.process("email/deactivation", context);
    }
    }
