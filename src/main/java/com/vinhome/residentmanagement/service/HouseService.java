package com.vinhome.residentmanagement.service;

import com.vinhome.residentmanagement.dtos.HouseDto;
import com.vinhome.residentmanagement.entity.House;
import com.vinhome.residentmanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface HouseService {
    HouseDto createHouse(HouseDto house);

    HouseDto getHouseById(Long houseId);

    List<HouseDto> getAllHouses();

    HouseDto updateHouse(Long id, HouseDto house);

    void deleteHouse(Long houseId);
}
