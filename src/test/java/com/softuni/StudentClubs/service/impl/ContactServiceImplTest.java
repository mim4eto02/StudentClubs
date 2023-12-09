package com.softuni.StudentClubs.service.impl;


import com.softuni.StudentClubs.models.dto.ContactDto;
import com.softuni.StudentClubs.models.entities.Contact;
import com.softuni.StudentClubs.repository.ContactRepository;
import com.softuni.StudentClubs.service.impl.ContactServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    public ContactServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveContact() {
        ContactDto contactDto = new ContactDto();
        contactDto.setName("Test Name");
        contactDto.setEmail("test@example.com");

        Contact savedContact = new Contact();
        savedContact.setId(1L);
        savedContact.setName("Test Name");
        savedContact.setEmail("test@example.com");

        when(contactRepository.save(any(Contact.class))).thenReturn(savedContact);

        Contact result = contactService.saveContact(contactDto);

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    public void testDeleteContact() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setCreatedOn(LocalDate.now().minusMonths(7));
        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setCreatedOn(LocalDate.now().minusMonths(5));

        List<Contact> contacts = List.of(contact1, contact2);

        when(contactRepository.findByCreatedOnBefore(sixMonthsAgo)).thenReturn(contacts);

        contactService.deleteContact();

        verify(contactRepository, times(1)).deleteAll(contacts);
    }
}
