package com.softuni.StudentClubs.service;

import com.softuni.StudentClubs.dto.ClubDto;
import com.softuni.StudentClubs.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();

    Club saveClub (ClubDto club);

    ClubDto findClubById(long clubId);

    void updateClub(ClubDto clubDto);

    void deleteClubById(long clubId);

    List<ClubDto> searchByTitle(String query);

    List<ClubDto> findClubsByUserId(Long id);
}
