package com.softuni.StudentClubs.mapper;

import com.softuni.StudentClubs.models.dto.ClubDto;
import com.softuni.StudentClubs.models.entities.Club;
import org.modelmapper.ModelMapper;

public class ClubMapper {

    private static final ModelMapper modelMapper = new ModelMapper();
    public static Club mapToClub(ClubDto clubDto) {
        return modelMapper.map(clubDto, Club.class);
    }

    public static ClubDto mapToClubDto(Club club) {
        return modelMapper.map(club, ClubDto.class);
    }
}
