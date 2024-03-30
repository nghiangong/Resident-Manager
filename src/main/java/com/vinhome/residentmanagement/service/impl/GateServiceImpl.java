package com.vinhome.residentmanagement.service.impl;

import com.vinhome.residentmanagement.dtos.GateDto;
import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.GateRepository;
import com.vinhome.residentmanagement.service.GateService;
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
        if(optionalGate.isPresent()){
            return mapStructMapper.gateToGateDto(optionalGate.get());
        }else{
            throw new EntityNotFoundException(Gate.class, "id", gateId.toString());
        }
    }

    @Override
    public List<GateDto> getAllGates() {
        List<Gate> gates = gateRepository.findAll();
        return mapStructMapper.gatesToGateDtos(gates);
    }

    @Override
    public GateDto updateGate(Long id, GateDto gateDto) {
        Optional<Gate> existingGate = gateRepository.findById(id);
        if(existingGate.isPresent()){
            Gate oldGate = existingGate.get();
            mapStructMapper.updateGateFromDTO(gateDto, oldGate);
            return mapStructMapper.gateToGateDto(gateRepository.save(oldGate));
        }else {
            throw new EntityNotFoundException(Gate.class, "id", id.toString());
        }
    }

    @Override
    public void deleteGate(Long gateId) {
        gateRepository.deleteById(gateId);
    }
}
