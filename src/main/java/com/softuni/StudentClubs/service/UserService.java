package com.softuni.StudentClubs.service;

import com.softuni.StudentClubs.dto.RegistrationDto;
import com.softuni.StudentClubs.dto.UserEditDto;
import com.softuni.StudentClubs.dto.UserViewDto;
import com.softuni.StudentClubs.models.entities.UserEntity;

import java.util.List;


public interface UserService {

    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    void editUser(UserEditDto userEditDto, String username);

    List<UserViewDto> getAllUsers();

}
