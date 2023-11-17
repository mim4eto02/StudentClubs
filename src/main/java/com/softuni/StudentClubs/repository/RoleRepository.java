package com.softuni.StudentClubs.repository;

import com.softuni.StudentClubs.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
