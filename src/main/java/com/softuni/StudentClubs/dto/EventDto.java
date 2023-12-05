package com.softuni.StudentClubs.dto;

import com.softuni.StudentClubs.models.entities.Club;
import com.softuni.StudentClubs.models.enums.EventTypeEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventDto {

    private Long id;

    @NotEmpty(message = "Event name is required.")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future(message = "Event start time must be in the future.")
    @NotNull(message = "Event start time is required.")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future(message = "Event end time must be in the future.")
    @NotNull(message = "Event end time is required.")
    private LocalDateTime endTime;

    private EventTypeEnum type;

    @Size(min = 5, max = 2000, message = "Photo URL must be between 5 and 2000 characters.")
    private String photoUrl;

    @Size(min = 5, max = 2000, message = "Description must be between 5 and 2000 characters.")
    private String description;

    private String location;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
    private Club club;

}