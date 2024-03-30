package com.vinhome.residentmanagement.controller;

import com.vinhome.residentmanagement.dtos.RoleDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.response.GenericResponse;
import com.vinhome.residentmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserGetDto> saveUser(@RequestBody UserPostDto user) throws RuntimeException {
        UserGetDto newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/role")
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleDto roleDto) {
        RoleDto newRole = userService.createRole(roleDto);
        return ResponseEntity.ok(newRole);
    }

    @GetMapping("/users")
    public List<UserGetDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable Long id) throws RuntimeException {
        UserGetDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable Long id, @RequestBody UserPostDto userPostDto) throws RuntimeException {
        UserGetDto updatedUser = userService.updateUser(id, userPostDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
