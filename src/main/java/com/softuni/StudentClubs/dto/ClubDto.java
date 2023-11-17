package com.softuni.StudentClubs.dto;

import com.softuni.StudentClubs.models.UserEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ClubDto {

    private Long id;

    @NotEmpty(message = "Club title is required.")
    private String title;

    @NotEmpty(message = "Club photo is required.")
    @Size(min = 5, max = 2000, message = "Photo URL must be between 14 and 2000 characters.")
    private String photoUrl;

    @NotEmpty(message = "Club content is required.")
    private String content;

    private UserEntity createdBy;

    private LocalDate createdOn;

    private LocalDate updatedOn;

    private List<EventDto> events;
}
