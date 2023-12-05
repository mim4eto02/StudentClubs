package com.softuni.StudentClubs.service.impl;


import com.softuni.StudentClubs.models.entities.Role;
import com.softuni.StudentClubs.repository.RoleRepository;
import com.softuni.StudentClubs.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


}
