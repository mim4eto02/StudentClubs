package com.softuni.StudentClubs.service.impl;

import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.repository.RoleRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final EmailServiceImpl emailService;
    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void makeAdmin(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user.getRoles().contains(roleRepository.findByName("ADMIN"))) {
            return;
        }

            user.getRoles().add(roleRepository.findByName("ADMIN"));
            userRepository.save(user);

    }

    @Override
    public void removeAdmin(Long userId) {
       UserEntity user = userRepository.findById(userId).orElse(null);
        user.getRoles().remove(roleRepository.findByName("ADMIN"));
        userRepository.save(user);
    }

    @Override
    public void activateUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setActive(true);
            this.emailService.sendActivationEmail(user.getEmail(), user.getUsername());
            userRepository.save(user);
        }

    }

    @Override
    public void deactivateUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setActive(false);
            this.emailService.sendDeactivationEmail(user.getEmail(), user.getUsername());
            userRepository.save(user);
        }

    }
}