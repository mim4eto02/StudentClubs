package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.dto.RegistrationDto;
import com.softuni.StudentClubs.dto.UserEditDto;
import com.softuni.StudentClubs.models.entities.Role;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.models.events.UserRegistrationEvent;
import com.softuni.StudentClubs.repository.RoleRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher eventPublisher;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

        this.eventPublisher = eventPublisher;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        userEntity.setRoles(Collections.singletonList(role));
        userEntity.setActive(true);
        userRepository.save(userEntity);

        eventPublisher.publishEvent(new UserRegistrationEvent(this, registrationDto.getUsername(), registrationDto.getEmail()));
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void editUser(UserEditDto userEditDto, String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setUsername(userEditDto.getUsername());
        userEntity.setEmail(userEditDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userEditDto.getPassword()));
        userRepository.save(userEntity);

    }





}
