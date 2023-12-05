package com.softuni.StudentClubs.repository;

import com.softuni.StudentClubs.models.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findClubByTitle(String title);

    @Query("SELECT c FROM Club c WHERE lower(c.title) LIKE lower(CONCAT('%',:query,'%'))")
    List<Club> searchByTitle(String query);

    @Query("SELECT c FROM Club c WHERE c.createdBy.id = :id")
    List<Club> findClubsByCreatedBy_Id(Long id);
}
