package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.dto.EventDto;
import com.softuni.StudentClubs.mapper.EventMapper;
import com.softuni.StudentClubs.models.Club;
import com.softuni.StudentClubs.models.Event;
import com.softuni.StudentClubs.repository.ClubRepository;
import com.softuni.StudentClubs.repository.EventRepository;
import com.softuni.StudentClubs.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.softuni.StudentClubs.mapper.EventMapper.mapToEvent;
import static com.softuni.StudentClubs.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {

    private final ClubRepository clubRepository;
    private final EventRepository eventRepository;

    public EventServiceImpl(ClubRepository clubRepository, EventRepository eventRepository) {
        this.clubRepository = clubRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
        Club club = clubRepository.findById(clubId).get();
        Event event = mapToEvent(eventDto);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllUpcomingEvents() {
        List<Event> events = eventRepository.findAllUpcomingEvents();
        return events.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
    }

    @Override
    public EventDto findEventById(long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        Event event = mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void deleteEventById(long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public List<EventDto> searchByTitle(String query) {
        List<Event> events = eventRepository.searchByTitle(query);
        return events.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> findAllPastEvents() {
        List<Event> events = eventRepository.findAllPastEvents();
        return events.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
    }


}
