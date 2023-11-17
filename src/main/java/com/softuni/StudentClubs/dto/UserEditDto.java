package com.softuni.StudentClubs.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {

    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    private boolean isActive;

    private List<RoleDto> roles;
}
