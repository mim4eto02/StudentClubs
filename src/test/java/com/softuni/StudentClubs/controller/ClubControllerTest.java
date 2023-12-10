package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.models.dto.ClubDto;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.service.ClubService;
import com.softuni.StudentClubs.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClubControllerTest {

    @Mock
    private ClubService clubService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ClubController clubController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clubController).build();
    }

    @Test
    void listClubs() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(clubService.findAllClubs()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/clubs"))
                .andExpect(status().isOk())
                .andExpect(view().name("clubs-list"))
                .andExpect(model().attributeExists("clubs"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(clubService, times(1)).findAllClubs();
    }

    @Test
    void clubDetail() throws Exception {
        long clubId = 1L;
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(clubService.findClubById(clubId)).thenReturn(new ClubDto());

        mockMvc.perform(get("/clubs/{clubId}", clubId))
                .andExpect(status().isOk())
                .andExpect(view().name("clubs-detail"))
                .andExpect(model().attributeExists("club"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(clubService, times(1)).findClubById(clubId);
    }

    @Test
    void createClubForm() throws Exception {
        mockMvc.perform(get("/clubs/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("clubs-create"))
                .andExpect(model().attributeExists("club"));

    }

    @Test
    void deleteClub() throws Exception {
        long clubId = 1L;

        mockMvc.perform(get("/clubs/{clubId}/delete", clubId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clubs"));

        verify(clubService, times(1)).deleteClubById(clubId);
    }

    @Test
    void searchClubs() throws Exception {
        String query = "club";
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(clubService.searchByTitle(query)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/clubs/search").param("query", query))
                .andExpect(status().isOk())
                .andExpect(view().name("clubs-list"))
                .andExpect(model().attributeExists("clubs"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(clubService, times(1)).searchByTitle(query);
    }

//    @Test
//    void myClubs() throws Exception {
//        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
//        when(clubService.findClubsByUserId(anyLong())).thenReturn(Collections.emptyList());
//

    @Test
    void editClubForm() throws Exception {
        long clubId = 1L;
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(clubService.findClubById(clubId)).thenReturn(new ClubDto());

        mockMvc.perform(get("/clubs/{clubId}/edit", clubId))
                .andExpect(status().isOk())
                .andExpect(view().name("clubs-edit"))
                .andExpect(model().attributeExists("club"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(clubService, times(1)).findClubById(clubId);
    }


}

