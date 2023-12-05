package com.softuni.StudentClubs.service;

import com.softuni.StudentClubs.dto.ContactDto;
import com.softuni.StudentClubs.models.entities.Contact;

public interface ContactService {

    Contact saveContact(ContactDto contact);

    void deleteContact();
}
