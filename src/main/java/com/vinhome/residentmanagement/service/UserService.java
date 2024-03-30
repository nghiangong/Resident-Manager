package com.vinhome.residentmanagement.service;

import com.vinhome.residentmanagement.dtos.RoleDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserGetDto createUser(UserPostDto user);

    UserGetDto getUserById(Long userId);

    List<UserGetDto> getAllUsers();

    UserGetDto updateUser(Long id, UserPostDto user);

    RoleDto createRole(RoleDto roleDto);

    void deleteUser(Long userId);
}
