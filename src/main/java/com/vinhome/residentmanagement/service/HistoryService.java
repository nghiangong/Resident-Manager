package com.vinhome.residentmanagement.service;

import com.vinhome.residentmanagement.dtos.DayStatistic;
import com.vinhome.residentmanagement.dtos.HistoryGetDto;
import com.vinhome.residentmanagement.dtos.HistoryPostDto;
import com.vinhome.residentmanagement.dtos.OverviewStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public interface HistoryService {
    HistoryGetDto createHistory(HistoryPostDto historyPostDto);
    HistoryGetDto getHistoryById(Long historyId);
    Page<HistoryGetDto> findAllHistories(int pageNumber, int pageSize, Long gateId);
    OverviewStatistic overviewStatistic(int month, int year);
    List<DayStatistic> monthStatistic(int month, int year);
    List<HistoryGetDto> getAllHistories(Long userId);
    int countUnreadNotification(Long userId);
    Long countEntryExitPerDay(Date date, Long userId);
    HistoryGetDto updateHistory(Long id, HistoryPostDto historyPostDto);
    void deleteHistory(Long historyId);
}
