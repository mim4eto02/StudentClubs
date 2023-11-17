package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.dto.RegistrationDto;
import com.softuni.StudentClubs.dto.RoleDto;
import com.softuni.StudentClubs.dto.UserEditDto;
import com.softuni.StudentClubs.dto.UserViewDto;
import com.softuni.StudentClubs.exception.UserNotFoundException;
import com.softuni.StudentClubs.models.Role;
import com.softuni.StudentClubs.models.UserEntity;
import com.softuni.StudentClubs.repository.RoleRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        userEntity.setRoles(Arrays.asList(role));
        userEntity.setActive(true);
        userRepository.save(userEntity);
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

    @Override
    public UserViewDto findById(Long id) {
        return userRepository.findById(id)
                .map(this::getUserViewDto)
                .orElse(null);
    }


    @Override
    public List<UserViewDto> getAllUsers() {
        return this.userRepository.findAll().stream()
                .map(this::getUserViewDto)
                .collect(Collectors.toList());
    }


    private UserViewDto getUserViewDto(UserEntity userEntity) {
        return new UserViewDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                getRoleDtoList(userEntity),
        userEntity.isActive());
    }

    private List<RoleDto> getRoleDtoList(UserEntity userEntity) {
        return userEntity.getRoles().stream()
                .map(Role::getName)
                .map(RoleDto::new)
                .collect(Collectors.toList());
    }


}
