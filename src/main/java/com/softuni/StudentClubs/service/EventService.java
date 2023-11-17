package com.softuni.StudentClubs.service;

import com.softuni.StudentClubs.dto.EventDto;

import java.util.List;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);

    List<EventDto> findAllEvents();

    EventDto findEventById(long eventId);

    void updateEvent(EventDto eventDto);

    void deleteEventById(long eventId);

    List<EventDto> searchByTitle(String query);
}
