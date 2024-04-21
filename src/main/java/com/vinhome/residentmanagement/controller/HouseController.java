package com.vinhome.residentmanagement.controller;

import com.vinhome.residentmanagement.dtos.GateDto;
import com.vinhome.residentmanagement.dtos.HouseDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.entity.House;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @PostMapping("/house")
    public ResponseEntity<HouseDto> saveHouse(@RequestBody HouseDto house) {
        HouseDto newHouse = houseService.createHouse(house);
        return ResponseEntity.ok(newHouse);
    }

    @GetMapping("/houses")
    public ResponseEntity<List<HouseDto>> getAllHouses() {
        return ResponseEntity.ok(houseService.getAllHouses());
    }

    @GetMapping("/houses/page")
    public ResponseEntity<Page<HouseDto>> getHouses(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(houseService.findAllHouse(pageNumber, pageSize));
    }

    @GetMapping("/houses/{id}")
    public ResponseEntity<HouseDto> getHouseById(@PathVariable Long id) throws RuntimeException {
        HouseDto house = houseService.getHouseById(id);
        return ResponseEntity.ok(house);
    }

    @PutMapping("/houses/{id}")
    public ResponseEntity<HouseDto> updateHouse(@PathVariable Long id, @RequestBody HouseDto houseDto) throws RuntimeException{
        HouseDto updatedHouse = houseService.updateHouse(id, houseDto);
        return ResponseEntity.ok(updatedHouse);
    }

    @DeleteMapping("/houses/{id}")
    public ResponseEntity<String> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return ResponseEntity.ok("House deleted successfully");
    }
}
