package com.softuni.StudentClubs.mapper;

import com.softuni.StudentClubs.dto.ContactDto;
import com.softuni.StudentClubs.models.entities.Contact;
import org.modelmapper.ModelMapper;

public class ContactMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Contact mapToContact(ContactDto contactDto) {
        Contact contact = modelMapper.map(contactDto, Contact.class);
        return contact;
    }
}
