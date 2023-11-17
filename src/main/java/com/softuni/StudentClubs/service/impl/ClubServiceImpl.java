package com.softuni.StudentClubs.service.impl;


import com.softuni.StudentClubs.dto.ClubDto;
import com.softuni.StudentClubs.mapper.ClubMapper;
import com.softuni.StudentClubs.models.Club;
import com.softuni.StudentClubs.models.UserEntity;
import com.softuni.StudentClubs.repository.ClubRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.ClubService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {


    private ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    @Override
    public List<ClubDto> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map(ClubMapper::mapToClubDto).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        Club club = ClubMapper.mapToClub(clubDto);
        club.setCreatedBy(userEntity);
        return clubRepository.save(club);
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepository.findById(clubId).get();
        return ClubMapper.mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        Club club = ClubMapper.mapToClub(clubDto);
        club.setCreatedBy(userEntity);
        clubRepository.save(club);

    }

    @Override
    public void deleteClubById(long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchByTitle(String query) {
        List<Club> clubs = clubRepository.searchByTitle(query);
        return clubs.stream().map(ClubMapper::mapToClubDto).collect(Collectors.toList());
    }

    @Override
    public List<ClubDto> findClubsByUserId(Long id) {
        List<Club> clubs = clubRepository.findClubsByCreatedBy_Id(id);
        return clubs.stream().map(ClubMapper::mapToClubDto).collect(Collectors.toList());
    }

}

