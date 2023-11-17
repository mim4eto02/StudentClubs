package com.softuni.StudentClubs.rest;

import com.softuni.StudentClubs.dto.UserViewDto;
import com.softuni.StudentClubs.exception.UserNotFoundException;
import com.softuni.StudentClubs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserViewDto> getUserInfo(@PathVariable("id") Long id) {

        UserViewDto userViewDto = this.userService.findById(id);

        if (userViewDto == null) {
            throw new UserNotFoundException(id);
        }

        return ResponseEntity.ok(userViewDto);
    }

    @GetMapping("")
      public ResponseEntity<List<UserViewDto>> getAllUsersInfo() {

            return ResponseEntity.ok(this.userService.getAllUsers());
        }
}