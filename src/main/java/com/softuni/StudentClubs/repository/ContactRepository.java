package com.softuni.StudentClubs.repository;

import com.softuni.StudentClubs.models.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByCreatedOnBefore(LocalDate date);
}
