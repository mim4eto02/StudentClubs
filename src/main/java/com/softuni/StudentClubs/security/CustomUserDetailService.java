package com.softuni.StudentClubs.security;

import com.softuni.StudentClubs.exception.InactiveUserException;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findFirstByUsername(username);
        if (userEntity != null) {
            if (!userEntity.isActive()) {
                throw new InactiveUserException("User is not active.");
            }
            User authUser = new User(
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
            );
            return authUser;
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}