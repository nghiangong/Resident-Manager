package com.vinhome.residentmanagement.mappers;

import com.vinhome.residentmanagement.dtos.*;
import com.vinhome.residentmanagement.entity.*;
import com.vinhome.residentmanagement.repository.HouseRepository;
import com.vinhome.residentmanagement.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
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
    @Mapping(source = "houseId", target = "house.id")
    @Mapping(source = "gateId", target = "gate.id")
    User userPostDtoToUser(UserPostDto userPostDto);

    @Mapping(target = "readStatus", ignore = true)
    @Mapping(source = "gateId", target = "gate.id")
    @Mapping(source = "qrCreatorId", target = "qrCreator.id")
    History historyPostDtoToHistory(HistoryPostDto historyPostDto);

    HistoryGetDto historyToHistoryGetDto(History history);

    List<UserGetDto> usersToUserGetDtos(List<User> users);

    List<HouseDto> housesToHouseDtos(List<House> houses);

    List<GateDto> gatesToGateDtos(List<Gate> gates);

    List<HistoryGetDto> historiesToHistoryGetDtos(List<History> histories);

    House houseDtoToHouse(HouseDto houseDto);

    HouseDto houseToHouseDto(House house);
    Role roleDtoToRole(RoleDto roleDto);

    GateDto gateToGateDto(Gate gate);
    Gate gateDtoToGate(GateDto gateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(source = "houseId", target = "house.id")
    @Mapping(source = "gateId", target = "gate.id")
    void updateUserFromDTO(UserPostDto userDTO, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "gateId", target = "gate.id")
    @Mapping(source = "qrCreatorId", target = "qrCreator.id")
    void updateHistoryFromDTO(HistoryPostDto historyDto, @MappingTarget History history);

    @Mapping(target = "id", ignore = true)
    void updateHouseFromDTO(HouseDto houseDto, @MappingTarget House house);

    @Mapping(target = "id", ignore = true)
    void updateGateFromDTO(GateDto gateDto, @MappingTarget Gate gate);
}