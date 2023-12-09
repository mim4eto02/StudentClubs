package com.softuni.StudentClubs.service.aop;

import com.softuni.StudentClubs.models.entities.Club;
import com.softuni.StudentClubs.models.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;  // Import SimpleMailMessage

import javax.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClubEmailAspectTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private ClubEmailAspect clubEmailAspect;

    @Test
    void sendEmailAfterSavingClub() {
        UserEntity createdBy = new UserEntity();
        createdBy.setEmail("test@example.com");

        Club club = new Club();
        club.setTitle("Test Club");
        club.setCreatedBy(createdBy);

        clubEmailAspect.sendEmailAfterSavingClub(club);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));  // Use SimpleMailMessage.class
    }
}
