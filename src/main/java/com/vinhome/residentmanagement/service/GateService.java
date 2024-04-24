package com.vinhome.residentmanagement.service;

import com.vinhome.residentmanagement.dtos.GateDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GateService {
    GateDto createGate(GateDto gateDto);
    GateDto getGateById(Long gateId);
    List<GateDto> getAllGates();
    Page<GateDto> findAllGate(int pageNumber, int pageSize, String keyword);
    GateDto updateGate(Long id, GateDto gateDto);
    void deleteGate(Long gateId);
}
