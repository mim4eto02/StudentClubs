package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.models.entities.Role;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.repository.RoleRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.service.impl.AdminServiceImpl;
import com.softuni.StudentClubs.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        adminService.getAllUsers();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        adminService.getUserById(userId);

        verify(userRepository, times(1)).findById(userId);
    }

//    @Test
//    void testMakeAdmin() {
//        Long userId = 1L;
//        UserEntity user = new UserEntity();
//        user.setId(userId);
//        user.setRoles(Collections.singletonList(new Role()));
//
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(roleRepository.findByName("ADMIN")).thenReturn(new Role());
//        adminService.makeAdmin(userId);
//
//        verify(userRepository, times(1)).findById(userId);
//        verify(userRepository, times(1)).save(user);
//    }

    @Test
    void testRemoveAdmin() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setRoles(Collections.singletonList(new Role()));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByName("ADMIN")).thenReturn(new Role());

        adminService.removeAdmin(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testActivateUser() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.activateUser(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(emailService, times(1)).sendActivationEmail(user.getEmail(), user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeactivateUser() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.deactivateUser(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(emailService, times(1)).sendDeactivationEmail(user.getEmail(), user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

}
