package com.softuni.StudentClubs.service.impl;


import com.softuni.StudentClubs.models.dto.ClubDto;
import com.softuni.StudentClubs.mapper.ClubMapper;
import com.softuni.StudentClubs.models.entities.Club;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.repository.ClubRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.ClubService;
import com.softuni.StudentClubs.service.EmailService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.softuni.StudentClubs.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {


    private final ClubRepository clubRepository;

    private final EmailService emailService;

    public ClubServiceImpl(ClubRepository clubRepository, EmailService emailService, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

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
    public void deleteClubById ( long clubId) throws NotFoundException {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<Club> clubOptional = clubRepository.findById(clubId);

            if (clubOptional.isPresent()) {
                Club club = clubOptional.get();
                String nameClub = club.getTitle();
                String email = club.getCreatedBy().getEmail();

                clubRepository.deleteById(clubId);

                if (authentication.getAuthorities().stream().anyMatch(
                        grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
                    emailService.sendDeletionEmail("Club", nameClub, email);
                }
            } else {
                throw new NotFoundException(clubId);
            }
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

