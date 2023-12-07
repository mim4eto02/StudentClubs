package com.softuni.StudentClubs.service;

import com.softuni.StudentClubs.dto.EventDto;
import org.springframework.hateoas.CollectionModel;

import java.util.Collection;
import java.util.List;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);

    List<EventDto> findAllUpcomingEvents();

    EventDto findEventById(long eventId);

    void updateEvent(EventDto eventDto);

    void deleteEventById(long eventId);

    List<EventDto> searchByTitle(String query);

    List<EventDto> findAllPastEvents();

    void sendCalendarInvitation(Long eventId, String email );

}
