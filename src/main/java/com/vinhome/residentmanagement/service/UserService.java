package com.vinhome.residentmanagement.service;

import com.vinhome.residentmanagement.dtos.PieChartStatistic;
import com.vinhome.residentmanagement.dtos.RoleDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.entity.User;
import org.springframework.data.domain.Page;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserGetDto createUser(UserPostDto user);

    UserGetDto getUserById(Long userId);

    List<UserGetDto> getAllUsers();

    List<RoleDto> getAllRoles();

    Page<UserGetDto> findAllUser(int pageNumber, int pageSize, boolean acceptedStatus, String keyword);
    PieChartStatistic statisticPieChart();

    Page<UserGetDto> findAllGateKeeper(int pageNumber, int pageSize, String keyword);

    List<UserGetDto> getFamilyMembers(Long ownId);

    UserGetDto updateUser(Long id, UserPostDto user) throws NoSuchAlgorithmException;

    RoleDto createRole(RoleDto roleDto);

    void deleteUser(Long userId);
}
