package com.softuni.StudentClubs.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @NotBlank(message = "Name must not be empty!")
   private String name;

    @NotBlank(message = "Email must not be empty!")
    @Email(message = "Invalid email!")
   private String email;

    @NotBlank(message = "Subject must not be empty!")
   private String subject;

    @NotBlank(message = "Message must not be empty!")
   private String message;


}
