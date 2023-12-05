package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.dto.ContactDto;
import com.softuni.StudentClubs.mapper.ContactMapper;
import com.softuni.StudentClubs.models.entities.Contact;
import com.softuni.StudentClubs.repository.ContactRepository;
import com.softuni.StudentClubs.service.ContactService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact saveContact(ContactDto contact) {
        Contact contactEntity = ContactMapper.mapToContact(contact);
        return contactRepository.save(contactEntity);
    }

    @Override
    public void deleteContact() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        List<Contact> contacts = contactRepository.findByCreatedOnBefore(sixMonthsAgo);
        contactRepository.deleteAll(contacts);
    }
}
