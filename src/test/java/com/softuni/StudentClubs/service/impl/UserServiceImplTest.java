package com.softuni.StudentClubs.service.impl;
import com.softuni.StudentClubs.models.dto.RegistrationDto;
import com.softuni.StudentClubs.models.dto.UserEditDto;
import com.softuni.StudentClubs.models.dto.UserViewDto;
import com.softuni.StudentClubs.models.entities.Role;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.repository.RoleRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private UserServiceImpl userService;

    public UserServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveUser() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("testUser");
        registrationDto.setEmail("test@example.com");
        registrationDto.setPassword("password");

        Role role = new Role();
        role.setName("USER");

        when(roleRepository.findByName("USER")).thenReturn(role);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        userService.saveUser(registrationDto);

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(eventPublisher, times(1)).publishEvent(any());

        // You can add more assertions based on your specific requirements
    }

    @Test
    void testGetAllUsers() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        List<UserEntity> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserViewDto> userViewDtos = userService.getAllUsers();

        assertEquals(2, userViewDtos.size());
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(userEntity);

        UserEntity foundUser = userService.findByEmail(email);

        assertEquals(userEntity, foundUser);
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(userEntity);

        UserEntity foundUser = userService.findByUsername(username);

        assertEquals(userEntity, foundUser);
    }

    @Test
    void testEditUser() {
        UserEditDto userEditDto = new UserEditDto();
        userEditDto.setUsername("newUsername");
        userEditDto.setEmail("newEmail@example.com");
        userEditDto.setPassword("newPassword");

        String username = "existingUser";
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername(username);
        existingUser.setEmail("oldEmail@example.com");
        existingUser.setPassword("oldPassword");

        when(userRepository.findByUsername(username)).thenReturn(existingUser);
        when(passwordEncoder.encode(userEditDto.getPassword())).thenReturn("encodedNewPassword");

        userService.editUser(userEditDto, username);

        assertEquals(userEditDto.getUsername(), existingUser.getUsername());
        assertEquals(userEditDto.getEmail(), existingUser.getEmail());
        assertEquals("encodedNewPassword", existingUser.getPassword());
        verify(userRepository, times(1)).save(existingUser);
    }
}
