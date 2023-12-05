package com.softuni.StudentClubs.repository;

import com.softuni.StudentClubs.models.entities.Role;
import com.softuni.StudentClubs.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT ur FROM roles ur join users u on ur.id = u.id where u.id = :id")
    List<Role> findRolesByUserId(@Param("id") Long id);

    @Query("SELECT ur FROM roles ur where ur.name = :role")
    Optional<Role> findByRole(@RequestParam("role") RoleEnum role);


    Role findByName(String user);
}
