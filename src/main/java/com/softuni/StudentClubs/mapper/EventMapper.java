package com.softuni.StudentClubs.mapper;

import com.softuni.StudentClubs.models.dto.EventDto;
import com.softuni.StudentClubs.models.entities.Event;
import org.modelmapper.ModelMapper;

public class EventMapper {

    private static final ModelMapper modelMapper = new ModelMapper();
    public static Event mapToEvent(EventDto eventDto) {
        return modelMapper.map(eventDto, Event.class);
    }

    public static EventDto mapToEventDto(Event event) {
        return modelMapper.map(event, EventDto.class);


    }
}