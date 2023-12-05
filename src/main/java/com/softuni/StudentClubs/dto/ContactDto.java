package com.softuni.StudentClubs.dto;

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

    @NotBlank(message = "Email must not be empty!")
    @Email(message = "Invalid email!")
   private String email;

    @NotBlank(message = "Subject must not be empty!")
   private String subject;

    @NotBlank(message = "Message must not be empty!")
   private String message;

    private LocalDate createdOn;



}
