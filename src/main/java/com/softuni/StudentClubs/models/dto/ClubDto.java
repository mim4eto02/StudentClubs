package com.softuni.StudentClubs.models.dto;

import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.models.enums.ClubTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubDto {

    private Long id;

    @NotEmpty(message = "Club title is required.")
    private String title;

    @NotEmpty(message = "Club photo is required.")
    @Size(min = 5, max = 2000, message = "Photo URL must be between 5 and 2000 characters.")
    private String photoUrl;

    @NotEmpty(message = "Club content is required.")
    private String content;

    private String address;

    private ClubTypeEnum type;

    private UserEntity createdBy;

    private LocalDate createdOn;

    private LocalDate updatedOn;

    private List<EventDto> events;
}
