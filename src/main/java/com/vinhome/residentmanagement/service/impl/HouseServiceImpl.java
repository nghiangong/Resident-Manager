package com.vinhome.residentmanagement.service.impl;

import com.vinhome.residentmanagement.dtos.GateDto;
import com.vinhome.residentmanagement.dtos.HouseDto;
import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.History;
import com.vinhome.residentmanagement.entity.House;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.HouseRepository;
import com.vinhome.residentmanagement.service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    public Page<HouseDto> findAllHouse(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<House> houses = houseRepository.findAllHouses();
        List<HouseDto> houseDtos = mapStructMapper.housesToHouseDtos(houses);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), houseDtos.size());
        return new PageImpl<>(houseDtos.subList(start, end), pageRequest, houseDtos.size());
    }

    @Override
    public HouseDto updateHouse(Long id, HouseDto newHouse) {
        Optional<House> existingHouse = houseRepository.findById(id);
        if (existingHouse.isPresent()) {
            House oldHouse = existingHouse.get();
            if (newHouse.getDeletedAt() != null && houseRepository.livingPeopleExist(id)) {
                throw new RuntimeException("Còn tồn tại người đang sinh sống");
            }
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
