package com.vinhome.residentmanagement.controller;

import com.vinhome.residentmanagement.dtos.GateDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.service.GateService;
import com.vinhome.residentmanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class GateController {
    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @PostMapping("/gate")
    public ResponseEntity<GateDto> saveGate(@RequestBody GateDto gateDto) {
        GateDto newGate = gateService.createGate(gateDto);
        return ResponseEntity.ok(newGate);
    }

    @GetMapping("/gates")
    public ResponseEntity<List<GateDto>> getAllGates() {
        return ResponseEntity.ok(gateService.getAllGates());
    }

    @GetMapping("/gates/page")
    public ResponseEntity<Page<GateDto>> getGates(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(gateService.findAllGate(pageNumber, pageSize));
    }

    @GetMapping("/gates/{id}")
    public ResponseEntity<GateDto> getGateById(@PathVariable Long id) throws RuntimeException {
        GateDto gateDto = gateService.getGateById(id);
        return ResponseEntity.ok(gateDto);
    }

    @PutMapping("/gates/{id}")
    public ResponseEntity<GateDto> updateGate(@PathVariable Long id, @RequestBody GateDto gateDto) throws RuntimeException{
        GateDto updatedGate = gateService.updateGate(id, gateDto);
        return ResponseEntity.ok(updatedGate);
    }

    @DeleteMapping("/gates/{id}")
    public ResponseEntity<String> deleteGate(@PathVariable Long id) {
        gateService.deleteGate(id);
        return ResponseEntity.ok("Gate deleted successfully");
    }
}
