package com.vinhome.residentmanagement.service;

import com.vinhome.residentmanagement.dtos.HistoryGetDto;
import com.vinhome.residentmanagement.dtos.HistoryPostDto;

import java.util.List;

public interface HistoryService {
    HistoryGetDto createHistory(HistoryPostDto historyPostDto);
    HistoryGetDto getHistoryById(Long historyId);
    List<HistoryGetDto> getAllHistories();
    HistoryGetDto updateHistory(Long id, HistoryPostDto historyPostDto);
    void deleteHistory(Long historyId);
}
