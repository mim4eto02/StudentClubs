package com.softuni.StudentClubs.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserViewDto {

    private Long id;

    private String username;

    private String email;

    private List<RoleDto> roles;

    private boolean isActive;
}
