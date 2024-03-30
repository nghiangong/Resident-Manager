package com.vinhome.residentmanagement.service.impl;

import com.vinhome.residentmanagement.dtos.HouseDto;
import com.vinhome.residentmanagement.entity.History;
import com.vinhome.residentmanagement.entity.House;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.HouseRepository;
import com.vinhome.residentmanagement.service.HouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {
    private HouseRepository houseRepository;
    private MapStructMapper mapStructMapper;

    public HouseServiceImpl(HouseRepository houseRepository, MapStructMapper mapStructMapper) {
        this.houseRepository = houseRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Override
    public HouseDto createHouse(HouseDto house) {
        House newHouse = mapStructMapper.houseDtoToHouse(house);
        return mapStructMapper.houseToHouseDto(houseRepository.save(newHouse));
    }

    @Override
    public HouseDto getHouseById(Long houseId) {
        Optional<House> optionalHouse = houseRepository.findById(houseId);
        if (optionalHouse.isPresent()) {
            return mapStructMapper.houseToHouseDto(optionalHouse.get());
        } else {
            throw new EntityNotFoundException(House.class, "id", houseId.toString());
        }
    }

    @Override
    public List<HouseDto> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        return mapStructMapper.housesToHouseDtos(houses);
    }

    @Override
    public HouseDto updateHouse(Long id, HouseDto newHouse) {
        Optional<House> existingHouse = houseRepository.findById(id);
        if (existingHouse.isPresent()) {
            House oldHouse = existingHouse.get();
            mapStructMapper.updateHouseFromDTO(newHouse, oldHouse);
            return mapStructMapper.houseToHouseDto(houseRepository.save(oldHouse));
        } else {
            throw new EntityNotFoundException(House.class, "id", id.toString());
        }
    }

    @Override
    public void deleteHouse(Long houseId) {
        houseRepository.deleteById(houseId);
    }
}
