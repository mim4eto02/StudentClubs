package com.softuni.StudentClubs.unitTests;

import com.softuni.StudentClubs.dto.EventDto;
import com.softuni.StudentClubs.models.entities.Club;
import com.softuni.StudentClubs.models.entities.Event;
import com.softuni.StudentClubs.repository.ClubRepository;
import com.softuni.StudentClubs.repository.EventRepository;
import com.softuni.StudentClubs.service.EmailService;
import com.softuni.StudentClubs.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEvent() {
        Long clubId = 1L;
        EventDto eventDto = new EventDto();
        eventDto.setName("name");

        Club club = new Club();
        club.setId(clubId);

        when(clubRepository.findById(clubId)).thenReturn(java.util.Optional.of(club));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        eventService.createEvent(clubId, eventDto);

        verify(clubRepository,times(1)).findById(clubId);
        verify(eventRepository,times(1)).save(any(Event.class));
    }

    @Test
    void testFindAllUpcomingEvents() {
        Event event1 = new Event();
        Event event2 = new Event();

        event1.setName("name1");
        event2.setName("name2");

        List<Event> events = Arrays.asList(event1, event2);

        when(eventRepository.findAllUpcomingEvents()).thenReturn(events);

        List<EventDto> result = eventService.findAllUpcomingEvents();

        assertEquals(events.size(), result.size());
        assertEquals(events.get(0).getName(), result.get(0).getName());
        assertEquals(events.get(1).getName(), result.get(1).getName());
    }

    @Test
    void testUpdateEvent() {
        EventDto eventDto = new EventDto();
        eventDto.setName("name");

        Event event = new Event();
        event.setId(1L);

        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        eventService.updateEvent(eventDto);

        verify(eventRepository,times(1)).save(any(Event.class));
    }

    @Test
    void testDeleteEventById() {
        Long eventId = 1L;

        eventService.deleteEventById(eventId);

        verify(eventRepository,times(1)).deleteById(eventId);
    }

    @Test
    void testSearchByTitle() {
        String query = "query";

        Event event1 = new Event();
        Event event2 = new Event();

        event1.setName("name1");
        event2.setName("name2");

        List<Event> events = Arrays.asList(event1, event2);

        when(eventRepository.searchByTitle(query)).thenReturn(events);

        List<EventDto> result = eventService.searchByTitle(query);

        assertEquals(events.size(), result.size());
        assertEquals(events.get(0).getName(), result.get(0).getName());
        assertEquals(events.get(1).getName(), result.get(1).getName());
    }

    @Test
    void testFindAllPastEvents() {
        Event event1 = new Event();
        Event event2 = new Event();

        event1.setName("name1");
        event2.setName("name2");

        List<Event> events = Arrays.asList(event1, event2);

        when(eventRepository.findAllPastEvents()).thenReturn(events);

        List<EventDto> result = eventService.findAllPastEvents();

        assertEquals(events.size(), result.size());
        assertEquals(events.get(0).getName(), result.get(0).getName());
        assertEquals(events.get(1).getName(), result.get(1).getName());
    }

    @Test
    void testSendCalendarInvitation() {
        Long eventId = 1L;
        String email = "email@test.com";

        Event event = new Event();
        event.setId(eventId);
        event.setName("name");
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusHours(1));

        when(eventRepository.findById(eventId)).thenReturn(java.util.Optional.of(event));

        assertDoesNotThrow(() -> eventService.sendCalendarInvitation(eventId, email));

        verify(eventRepository,times(1)).findById(eventId);
        verify(emailService,times(1)).sendEmailWithAttachment(eq(email), anyString(), anyString(), any(byte[].class), anyString());
    }

    @Test
    void testFindEventById() {
        Long eventId = 1L;

        Event event = new Event();
        event.setId(eventId);
        event.setName("name");

        when(eventRepository.findById(eventId)).thenReturn(java.util.Optional.of(event));

        EventDto result = eventService.findEventById(eventId);

        assertEquals(event.getId(), result.getId());
        assertEquals(event.getName(), result.getName());
    }
}
