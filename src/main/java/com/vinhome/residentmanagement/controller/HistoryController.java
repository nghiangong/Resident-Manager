package com.vinhome.residentmanagement.controller;

import com.vinhome.residentmanagement.dtos.HistoryGetDto;
import com.vinhome.residentmanagement.dtos.HistoryPostDto;
import com.vinhome.residentmanagement.dtos.UserGetDto;
import com.vinhome.residentmanagement.dtos.UserPostDto;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.service.HistoryService;
import com.vinhome.residentmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping("/history")
    public ResponseEntity<HistoryGetDto> saveHistory(@RequestBody HistoryPostDto historyPostDto) {
        HistoryGetDto newHistory = historyService.createHistory(historyPostDto);
        return ResponseEntity.ok(newHistory);
    }

    @GetMapping("/histories")
    public List<HistoryGetDto> getAllHistories() {
        return historyService.getAllHistories();
    }

    @GetMapping("/histories/{id}")
    public ResponseEntity<HistoryGetDto> getHistoryById(@PathVariable Long id) throws RuntimeException {
        HistoryGetDto historyGetDto = historyService.getHistoryById(id);
        return ResponseEntity.ok(historyGetDto);
    }

    @PutMapping("/histories/{id}")
    public ResponseEntity<HistoryGetDto> updateHistory(@PathVariable Long id, @RequestBody HistoryPostDto historyPostDto) throws RuntimeException{
        HistoryGetDto updatedHistory = historyService.updateHistory(id, historyPostDto);
        return ResponseEntity.ok(updatedHistory);
    }

    @DeleteMapping("/histories/{id}")
    public ResponseEntity<String> deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
        return ResponseEntity.ok("History deleted successfully");
    }
}
