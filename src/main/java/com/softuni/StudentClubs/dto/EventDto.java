package com.softuni.StudentClubs.dto;

import com.softuni.StudentClubs.models.Club;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventDto {

    private Long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    private String type;

    @Size(min = 5, max = 2000, message = "Photo URL must be between 5 and 2000 characters.")
    private String photoUrl;

    @Size(min = 5, max = 2000, message = "Description must be between 5 and 2000 characters.")
    private String description;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
    private Club club;

}
