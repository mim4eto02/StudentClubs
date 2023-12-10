package com.softuni.StudentClubs.controller;
import com.softuni.StudentClubs.service.ClubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Mock
    private ClubService clubService;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @Test
    void privacy_shouldReturnPrivacyView() {
        // When
        ModelAndView modelAndView = homeController.privacy();

        // Then
        assertEquals("privacy", modelAndView.getViewName());
    }

    @Test
    void terms_shouldReturnTermsView() {
        // When
        ModelAndView modelAndView = homeController.terms();

        // Then
        assertEquals("terms", modelAndView.getViewName());
    }

    @Test
    void about_shouldReturnAboutView() {
        // When
        ModelAndView modelAndView = homeController.about();

        // Then
        assertEquals("about", modelAndView.getViewName());
    }
}
