package com.softuni.StudentClubs.dto;

import com.softuni.StudentClubs.models.entities.Role;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserViewDto {

    private Long id;

    private String username;

    private String email;

    private List<Role> roles;

    private boolean isActive;
}
