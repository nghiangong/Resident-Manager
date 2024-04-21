package com.vinhome.residentmanagement.mappers;

import com.vinhome.residentmanagement.dtos.*;
import com.vinhome.residentmanagement.entity.*;
import com.vinhome.residentmanagement.repository.HouseRepository;
import com.vinhome.residentmanagement.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {
    UserGetDto userToUserGetDto(User user);

    RoleDto roleToRoleDto(Role role);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "acceptedStatus", ignore = true)
    @Mapping(target = "house", ignore = true)
    @Mapping(target = "gate", ignore = true)
    User userPostDtoToUser(UserPostDto userPostDto);

    @Mapping(target = "readStatus", ignore = true)
    @Mapping(target = "gate", ignore = true)
    @Mapping(target = "qrCreator", ignore = true)
    History historyPostDtoToHistory(HistoryPostDto historyPostDto);

    HistoryGetDto historyToHistoryGetDto(History history);

    List<UserGetDto> usersToUserGetDtos(List<User> users);

    List<HouseDto> housesToHouseDtos(List<House> houses);

    List<GateDto> gatesToGateDtos(List<Gate> gates);
    List<RoleDto> rolesToRoleDtos(List<Role> roles);

    List<HistoryGetDto> historiesToHistoryGetDtos(List<History> histories);

    House houseDtoToHouse(HouseDto houseDto);

    HouseDto houseToHouseDto(House house);
    Role roleDtoToRole(RoleDto roleDto);

    GateDto gateToGateDto(Gate gate);
    Gate gateDtoToGate(GateDto gateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "house", ignore = true)
    @Mapping(target = "gate", ignore = true)
    void updateUserFromDTO(UserPostDto userDTO, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gate", ignore = true)
    @Mapping(target = "qrCreator", ignore = true)
    void updateHistoryFromDTO(HistoryPostDto historyDto, @MappingTarget History history);

    @Mapping(target = "id", ignore = true)
    void updateHouseFromDTO(HouseDto houseDto, @MappingTarget House house);

    @Mapping(target = "id", ignore = true)
    void updateGateFromDTO(GateDto gateDto, @MappingTarget Gate gate);
}