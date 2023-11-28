package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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

    private String generateMessageContentRegistration(String username) {
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
            mimeMessageHelper.setTo("sstudent_clubs@example.com");
            mimeMessageHelper.setSubject(subject);

            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(name).append(System.lineSeparator());
            sb.append("Email: ").append(email).append(System.lineSeparator());
            sb.append("Message: ").append(message).append(System.lineSeparator());

            mimeMessageHelper.setText(sb.toString());

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
