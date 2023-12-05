package com.softuni.StudentClubs.service;

import com.softuni.StudentClubs.models.entities.UserEntity;

import java.util.List;

public interface AdminService {

    List<UserEntity> getAllUsers();

    UserEntity getUserById(Long userId);

    void makeAdmin(Long userId);

    void removeAdmin(Long userId);

    void activateUser(Long userId);

    void deactivateUser(Long userId);
}
