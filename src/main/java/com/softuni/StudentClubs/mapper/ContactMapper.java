package com.softuni.StudentClubs.mapper;

import com.softuni.StudentClubs.dto.ContactDto;
import com.softuni.StudentClubs.models.entities.Contact;

public class ContactMapper {

    public static Contact mapToContact(ContactDto contactDto) {
        Contact contact = Contact.builder()
                .id(contactDto.getId())
                .name(contactDto.getName())
                .email(contactDto.getEmail())
                .subject(contactDto.getSubject())
                .message(contactDto.getMessage())
                .createdOn(contactDto.getCreatedOn())
                .build();
        return contact;
    }
}
