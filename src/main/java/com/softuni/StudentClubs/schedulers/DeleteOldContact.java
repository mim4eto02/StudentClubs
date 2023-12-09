package com.softuni.StudentClubs.schedulers;

import com.softuni.StudentClubs.service.ContactService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteOldContact {

    private final ContactService contactService;

    public DeleteOldContact(ContactService contactService) {
        this.contactService = contactService;
    }

    @Scheduled(cron = "0 0 00 1 1/6 ?")
    public void deleteOldContact() {
        contactService.deleteContact();
    }
}
