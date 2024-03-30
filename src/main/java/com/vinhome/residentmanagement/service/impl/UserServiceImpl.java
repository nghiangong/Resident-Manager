package com.vinhome.residentmanagement.service.impl;

import com.vinhome.residentmanagement.dtos.RoleDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.entity.Role;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.RoleRepository;
import com.vinhome.residentmanagement.repository.UserRepository;
import com.vinhome.residentmanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private MapStructMapper mapStructMapper;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, MapStructMapper mapStructMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserGetDto createUser(UserPostDto user) {
        if (user.getRoleIds().isEmpty()) {
            throw new NullPointerException("Người dùng chưa có vai trò");
        } else {
            User newUser = mapStructMapper.userPostDtoToUser(user);
            for (Long id : user.getRoleIds()) {
                Role newRole = roleRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Role.class, "id", id.toString()));
                newUser.addRole(newRole);
            }
            return mapStructMapper.userToUserGetDto(userRepository.save(newUser));
        }
    }

    @Override
    public UserGetDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return mapStructMapper.userToUserGetDto(optionalUser.get());
        } else {
            throw new EntityNotFoundException(User.class, "id", userId.toString());
        }
    }

    @Override
    public List<UserGetDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapStructMapper.usersToUserGetDtos(users);
    }

    @Override
    public UserGetDto updateUser(Long id, UserPostDto newUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User oldUser = existingUser.get();
            mapStructMapper.updateUserFromDTO(newUser, oldUser);
            if (newUser.getRoleIds().isEmpty()) {
                throw new NullPointerException("Người dùng chưa có vai trò");
            } else {
                oldUser.clearRoles();
                for (Long idDto : newUser.getRoleIds()) {
                    Role newRole = roleRepository.findById(idDto)
                            .orElseThrow(() -> new EntityNotFoundException(Role.class, "id", idDto.toString()));
                    oldUser.addRole(newRole);
                }
                return mapStructMapper.userToUserGetDto(userRepository.save(oldUser));
            }
        } else {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role newRole = mapStructMapper.roleDtoToRole(roleDto);
        return mapStructMapper.roleToRoleDto(roleRepository.save(newRole));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
