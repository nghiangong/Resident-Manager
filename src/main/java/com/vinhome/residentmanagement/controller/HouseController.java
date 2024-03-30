package com.vinhome.residentmanagement.controller;

import com.vinhome.residentmanagement.dtos.HouseDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.entity.House;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<HouseDto> getAllHouses() {
        return houseService.getAllHouses();
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
