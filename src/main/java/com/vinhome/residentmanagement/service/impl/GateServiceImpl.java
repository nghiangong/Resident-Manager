package com.vinhome.residentmanagement.service.impl;

import com.vinhome.residentmanagement.dtos.GateDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.GateRepository;
import com.vinhome.residentmanagement.service.GateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GateServiceImpl implements GateService {
    private GateRepository gateRepository;
    private MapStructMapper mapStructMapper;

    public GateServiceImpl(GateRepository gateRepository, MapStructMapper mapStructMapper) {
        this.gateRepository = gateRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Override
    public GateDto createGate(GateDto gateDto) {
        Gate newGate = mapStructMapper.gateDtoToGate(gateDto);
        return mapStructMapper.gateToGateDto(gateRepository.save(newGate));
    }

    @Override
    public GateDto getGateById(Long gateId) {
        Optional<Gate> optionalGate = gateRepository.findById(gateId);
        if (optionalGate.isPresent()) {
            return mapStructMapper.gateToGateDto(optionalGate.get());
        } else {
            throw new EntityNotFoundException(Gate.class, "id", gateId.toString());
        }
    }

    @Override
    public List<GateDto> getAllGates() {
        List<Gate> gates = gateRepository.findAll();
        return mapStructMapper.gatesToGateDtos(gates);
    }

    @Override
    public Page<GateDto> findAllGate(int pageNumber, int pageSize, String keyword) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Gate> gates = gateRepository.findAllGates(keyword);
        List<GateDto> gateDtos = mapStructMapper.gatesToGateDtos(gates);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), gateDtos.size());
        return new PageImpl<>(gateDtos.subList(start, end), pageRequest, gateDtos.size());
    }

    @Override
    public GateDto updateGate(Long id, GateDto gateDto) {
        Optional<Gate> existingGate = gateRepository.findById(id);
        if (existingGate.isPresent()) {
            Gate oldGate = existingGate.get();
            if (gateDto.getDeletedAt() != null && gateRepository.workingPeopleExist(id)) {
                throw new RuntimeException("Còn tồn tại người đang làm việc tại cổng");
            }
            mapStructMapper.updateGateFromDTO(gateDto, oldGate);
            return mapStructMapper.gateToGateDto(gateRepository.save(oldGate));
        } else {
            throw new EntityNotFoundException(Gate.class, "id", id.toString());
        }
    }

    @Override
    public void deleteGate(Long gateId) {
        gateRepository.deleteById(gateId);
    }
}
