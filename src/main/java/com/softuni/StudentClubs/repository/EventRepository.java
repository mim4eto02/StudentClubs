package com.softuni.StudentClubs.repository;

import com.softuni.StudentClubs.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE lower(e.name) LIKE lower(CONCAT('%',:query,'%'))")
    List<Event> searchByTitle(String query);
}
