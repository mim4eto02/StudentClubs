package com.softuni.StudentClubs.service.impl;


import com.softuni.StudentClubs.models.entities.Role;
import com.softuni.StudentClubs.models.enums.RoleEnum;
import com.softuni.StudentClubs.repository.RoleRepository;
import com.softuni.StudentClubs.service.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void initRoles() {
        createRoleIfNotExist("ADMIN");
        createRoleIfNotExist("USER");
    }

    @Override
    @Transactional
    public void createRoleIfNotExist(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }



}
