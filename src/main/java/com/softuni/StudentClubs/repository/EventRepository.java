package com.softuni.StudentClubs.repository;

import com.softuni.StudentClubs.models.entities.Club;
import com.softuni.StudentClubs.models.entities.Event;
import com.softuni.StudentClubs.models.enums.EventTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE lower(e.name) LIKE lower(CONCAT('%',:query,'%'))")
    List<Event> searchByTitle(String query);

    @Query("SELECT e FROM Event e WHERE e.endTime < CURRENT_TIMESTAMP")
    List<Event> findAllPastEvents();

    @Query("SELECT e FROM Event e WHERE e.endTime > CURRENT_TIMESTAMP")
    List<Event> findAllUpcomingEvents();

}
