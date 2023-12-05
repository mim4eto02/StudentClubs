package com.softuni.StudentClubs.mapper;

import com.softuni.StudentClubs.dto.ClubDto;
import com.softuni.StudentClubs.models.entities.Club;

import java.util.stream.Collectors;

import static com.softuni.StudentClubs.mapper.EventMapper.mapToEventDto;

public class ClubMapper {
    public static Club mapToClub(ClubDto clubDto) {
        Club club = Club.builder()
                .id(clubDto.getId())
                .title(clubDto.getTitle())
                .photoUrl(clubDto.getPhotoUrl())
                .content(clubDto.getContent())
                .type(clubDto.getType())
                .address(clubDto.getAddress())
                .createdBy(clubDto.getCreatedBy())
                .createdOn(clubDto.getCreatedOn())
                .updatedOn(clubDto.getUpdatedOn())
                .build();

        return club;
    }

    public static ClubDto mapToClubDto(Club club) {
        ClubDto clubDto = ClubDto.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .type(club.getType())
                .address(club.getAddress())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents().stream().map((event) -> mapToEventDto(event)).collect(Collectors.toList()))
                .build();

        return clubDto;
    }
}
