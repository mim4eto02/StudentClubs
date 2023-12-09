package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.controller.EventController;
import com.softuni.StudentClubs.models.dto.EventDto;
import com.softuni.StudentClubs.mapper.EventMapper;
import com.softuni.StudentClubs.models.entities.Club;
import com.softuni.StudentClubs.models.entities.Event;
import com.softuni.StudentClubs.models.enums.EventTypeEnum;
import com.softuni.StudentClubs.repository.ClubRepository;
import com.softuni.StudentClubs.repository.EventRepository;
import com.softuni.StudentClubs.service.EmailService;
import com.softuni.StudentClubs.service.EventService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.softuni.StudentClubs.mapper.EventMapper.mapToEvent;
import static com.softuni.StudentClubs.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {

    private final ClubRepository clubRepository;
    private final EventRepository eventRepository;

    private final EmailService emailService;

    public EventServiceImpl(ClubRepository clubRepository, EventRepository eventRepository, EmailService emailService) {
        this.clubRepository = clubRepository;
        this.eventRepository = eventRepository;
        this.emailService = emailService;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
        Club club = clubRepository.findById(clubId).get();
        Event event = mapToEvent(eventDto);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public CollectionModel<EventDto> findAllUpcomingEvents() {
        List<Event> events = eventRepository.findAllUpcomingEvents();
        List<EventDto> eventDtos = events.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());

        for (EventDto eventDto : eventDtos) {
            Link selfLink = WebMvcLinkBuilder.linkTo(EventController.class).slash(eventDto.getId()).withSelfRel();
            eventDto.add(selfLink);
        }

        Link collectionLink = WebMvcLinkBuilder.linkTo(EventController.class).withSelfRel();
        CollectionModel<EventDto> eventDtoCollectionModel = CollectionModel.of(eventDtos, collectionLink);

        return eventDtoCollectionModel;
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

    public void deleteEventById(long eventId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found!"));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nameEvent = event.getName();
            String email = event.getClub().getCreatedBy().getEmail();
            eventRepository.deleteById(eventId);

            if (authentication.getAuthorities().stream().anyMatch(
                    grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
                emailService.sendDeletionEmail("Event", nameEvent, email);
            }
        } catch (EntityNotFoundException ex) {
            // Log the exception or handle it as needed
            // You might want to rethrow a custom exception if necessary
            throw new com.softuni.StudentClubs.exception.NotFoundException(eventId);
        }
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

    @Override
    public void sendCalendarInvitation(Long eventId, String email) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found!"));

        try {
            String subject = "Invitation to Event: " + event.getName();
            String body = generateInvitationEmailContent(event);
            byte[] calendarAttachment = generateICalendarAttachment(event);

            emailService.sendEmailWithAttachment(email, subject, body, calendarAttachment, event.getName() + ".ics");
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            throw new RuntimeException("Unexpected error sending calendar invitation", e);
        }
    }



    private String generateInvitationEmailContent(Event event) {
         String sb = "You have been invited to the following event: " + event.getName() + System.lineSeparator() +
                 "Start time: " + formatDateTime(event.getStartTime()) + System.lineSeparator() +
                 "End time: " + formatDateTime(event.getEndTime()) + System.lineSeparator() +
                 "Location: " + event.getLocation() + System.lineSeparator() +
                 "Description: " + event.getDescription() + System.lineSeparator() +
                 "Please find attached the event invitation in iCalendar format.";

        return sb;
    }

    private byte[] generateICalendarAttachment(Event event) {
        String calendarContent = generateICalendarContent(event);
        return calendarContent.getBytes(StandardCharsets.UTF_8);
    }

    private String generateICalendarContent(Event event) {

        String sb = "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "BEGIN:VEVENT\n" +
                "SUMMARY:" + event.getName() + "\n" +
                "DESCRIPTION:" + event.getDescription() + "\n" +
                "DTSTART:" + formatDateTime(event.getStartTime()) + "\n" +
                "DTEND:" + formatDateTime(event.getEndTime()) + "\n" +
                "LOCATION:" + event.getLocation() + "\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR\n";

        return sb;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
        return dateTime.format(formatter);
    }

}
