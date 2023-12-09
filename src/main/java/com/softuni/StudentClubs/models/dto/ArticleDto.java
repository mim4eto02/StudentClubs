package com.softuni.StudentClubs.models.dto;

import com.softuni.StudentClubs.models.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

        private Long id;

        @NotEmpty(message = "Article title is required.")
        private String title;

        @NotEmpty(message = "Article photo is required.")
        private String photoUrl;

        @NotEmpty(message = "Article content is required.")
        @Size(min=10, message = "Article content must be at least 10 characters.")
        private String content;

        private UserEntity author;

        private String createdOn;

        private String updatedOn;

}
