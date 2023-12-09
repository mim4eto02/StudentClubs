package com.softuni.StudentClubs.models.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private Long id;
    @NotBlank(message = "Name must not be empty!")
   private String name;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
   private String email;

    @NotBlank(message = "Subject must not be empty!")
   private String subject;

    @NotBlank(message = "Message must not be empty!")
   private String message;

    private LocalDate createdOn;



}
