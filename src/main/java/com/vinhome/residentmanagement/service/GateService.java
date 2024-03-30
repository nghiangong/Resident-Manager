package com.vinhome.residentmanagement.service;

import com.vinhome.residentmanagement.dtos.GateDto;

import java.util.List;

public interface GateService {
    GateDto createGate(GateDto gateDto);
    GateDto getGateById(Long gateId);
    List<GateDto> getAllGates();
    GateDto updateGate(Long id, GateDto gateDto);
    void deleteGate(Long gateId);
}
