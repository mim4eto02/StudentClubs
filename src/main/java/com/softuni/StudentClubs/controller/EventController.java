package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.models.dto.EventDto;
import com.softuni.StudentClubs.models.entities.Event;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.models.enums.EventTypeEnum;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.EventService;
import com.softuni.StudentClubs.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EventController {

    private final EventService eventService;

    private final UserService userService;


    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/events")
    public String eventsList(Model model) {
        UserEntity user = new UserEntity();
        CollectionModel<EventDto> events = eventService.findAllUpcomingEvents();

        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);

        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") long eventId, Model model) {
        UserEntity user = new UserEntity();
        EventDto event = eventService.findEventById(eventId);
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("event", event);
        return "events-detail";
    }

    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model) {
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") long eventId, Model model) {
        EventDto event = eventService.findEventById(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }

    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") long eventId) {
        eventService.deleteEventById(eventId);
        return "redirect:/events";
    }

    @GetMapping("/events/search")
    public String eventsSearch(@RequestParam(value = "query") String query, Model model) {
        UserEntity user = new UserEntity();
        List<EventDto> events = eventService.searchByTitle(query);
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/past")
    public String pastEvents(Model model) {
        UserEntity user = new UserEntity();
        List<EventDto> events = eventService.findAllPastEvents();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";

    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId, @Valid @ModelAttribute("event") EventDto eventDto,
                              BindingResult result,
                              Model model) {
        if(result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "events-create";
        }
        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/" + clubId;
    }

    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Long eventId,
                              @Valid @ModelAttribute("event") EventDto event,
                              BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("event", event);
            return "events-edit";
        }
        EventDto eventDto = eventService.findEventById(eventId);
        event.setId(eventId);
        event.setClub(eventDto.getClub());
        eventService.updateEvent(event);
        return "redirect:/events";
    }

    @PostMapping("/events/{eventId}/invite")
    public String sendInvitation(@PathVariable("eventId") Long eventId, Model model) {

        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            try {
                eventService.sendCalendarInvitation(eventId, email);
                model.addAttribute("invitationMessage", "Invitation sent");
            } catch (Exception e) {
                model.addAttribute("invitationMessage", "Error sending invitations: " + e.getMessage());
            }
        }
        return "redirect:/events/" + eventId + "?success";
    }
}
