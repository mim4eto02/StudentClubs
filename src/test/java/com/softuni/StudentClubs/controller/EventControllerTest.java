package com.softuni.StudentClubs.controller;
import com.softuni.StudentClubs.models.dto.EventDto;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.EventService;
import com.softuni.StudentClubs.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @InjectMocks
    private EventController eventController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void eventsList() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(eventService.findAllUpcomingEvents()).thenReturn(CollectionModel.of(Collections.emptyList()));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("events-list"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("user"));


        verify(eventService, times(1)).findAllUpcomingEvents();
        verify(userService, times(1)).findByUsername(anyString());
    }

    @Test
    void viewEvent() throws Exception {
        long eventId = 1L;
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(eventService.findEventById(eventId)).thenReturn(new EventDto());

        mockMvc.perform(get("/events/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(view().name("events-detail"))
                .andExpect(model().attributeExists("event"))
                .andExpect(model().attributeExists("user"));

        verify(eventService, times(1)).findEventById(eventId);
        verify(userService, times(1)).findByUsername(anyString());
    }

    @Test
    void createEventForm() throws Exception {
        long clubId = 1L;

        mockMvc.perform(get("/events/{clubId}/new", clubId))
                .andExpect(status().isOk())
                .andExpect(view().name("events-create"))
                .andExpect(model().attributeExists("clubId"))
                .andExpect(model().attributeExists("event"));
    }

    @Test
    void deleteEvent() throws Exception {
        long eventId = 1L;

        mockMvc.perform(get("/events/{eventId}/delete", eventId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/events"));

        verify(eventService, times(1)).deleteEventById(eventId);
    }

    @Test
    void editEventForm() throws Exception {
        long eventId = 1L;
        when(eventService.findEventById(eventId)).thenReturn(new EventDto());

        mockMvc.perform(get("/events/{eventId}/edit", eventId))
                .andExpect(status().isOk())
                .andExpect(view().name("events-edit"))
                .andExpect(model().attributeExists("event"));

        verify(eventService, times(1)).findEventById(eventId);
    }

    @Test
    void eventsSearch() throws Exception {
        String query = "event";
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(eventService.searchByTitle(query)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/events/search").param("query", query))
                .andExpect(status().isOk())
                .andExpect(view().name("events-list"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(eventService, times(1)).searchByTitle(query);
    }

    @Test
    void pastEvents() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(new UserEntity());
        when(eventService.findAllPastEvents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/events/past"))
                .andExpect(status().isOk())
                .andExpect(view().name("events-list"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findByUsername(anyString());
        verify(eventService, times(1)).findAllPastEvents();
    }

//    @Test
//    void createEvent() throws Exception {
//        long clubId = 1L;
//        EventDto eventDto = new EventDto();
//        eventDto.setName("Test Event");
//
//
//}

}

