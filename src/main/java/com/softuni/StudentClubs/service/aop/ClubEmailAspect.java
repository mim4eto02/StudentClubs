package com.softuni.StudentClubs.service.aop;

import com.softuni.StudentClubs.models.entities.Club;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ClubEmailAspect {

    private final JavaMailSender javaMailSender;

    public ClubEmailAspect(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @AfterReturning(pointcut = "execution(* com.softuni.StudentClubs.service.ClubService.saveClub(..))", returning = "club")
    public void sendEmailAfterSavingClub(Club club) {
        String email = club.getCreatedBy().getEmail();

        String subject = "Club created successfully!";
        String body = "You have successfully created a club with name: " + club.getTitle() + "!";

        sendEmail(email, subject, body);

}

    private void sendEmail(String email, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("student-clubs@example.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
    }
    }
