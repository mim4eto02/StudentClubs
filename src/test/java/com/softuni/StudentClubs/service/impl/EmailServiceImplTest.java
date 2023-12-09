package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmailServiceImplTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private TemplateEngine templateEngine;

//    @Test
//    void testSendRegistrationEmail() {
//        String email = "test@example.com";
//        String username = "testUser";
//
//        when(templateEngine.process(any(String.class), any(IContext.class))).thenReturn("Mocked HTML Content");
//        emailService.sendRegistrationEmail(email, username);
//
//        verify(javaMailSender).send((MimeMessage) any());    }
//
//
//    @Test
//    void testSendEmailWithAttachment() {
//        // Mock data
//        String email = "test@example.com";
//        String subject = "Test Subject";
//        String body = "Test Body";
//        byte[] attachment = "Test Attachment".getBytes();
//        String attachmentName = "testAttachment.txt";
//
//        emailService.sendEmailWithAttachment(email, subject, body, attachment, attachmentName);
//
//        verify(javaMailSender).send((MimeMessage) any());    }
//
//    @Test
//    void testSendDeletionEmail() {
//        String entityName = "TestEntity";
//        String entityTitle = "Test Title";
//        String email = "test@example.com";
//
//        emailService.sendDeletionEmail(entityName, entityTitle, email);
//
//        verify(javaMailSender).send((MimeMessage) any());    }

}

