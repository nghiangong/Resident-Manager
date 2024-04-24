package com.vinhome.residentmanagement.service.impl;

import com.vinhome.residentmanagement.dtos.DayStatistic;
import com.vinhome.residentmanagement.dtos.HistoryGetDto;
import com.vinhome.residentmanagement.dtos.HistoryPostDto;
import com.vinhome.residentmanagement.dtos.OverviewStatistic;
import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.History;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.GateRepository;
import com.vinhome.residentmanagement.repository.HistoryRepository;
import com.vinhome.residentmanagement.repository.UserRepository;
import com.vinhome.residentmanagement.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryServiceImpl implements HistoryService {
    private HistoryRepository historyRepository;
    private GateRepository gateRepository;
    private UserRepository userRepository;
    private MapStructMapper mapStructMapper;

    public HistoryServiceImpl(HistoryRepository historyRepository, GateRepository gateRepository, UserRepository userRepository, MapStructMapper mapStructMapper) {
        this.historyRepository = historyRepository;
        this.gateRepository = gateRepository;
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Override
    public HistoryGetDto createHistory(HistoryPostDto historyPostDto) {
        History newHistory = mapStructMapper.historyPostDtoToHistory(historyPostDto);
        User newUser = userRepository.findById(historyPostDto.getQrCreatorId()).orElseThrow(() -> new EntityNotFoundException(User.class, "id", historyPostDto.getQrCreatorId().toString()));
        newHistory.setQrCreator(newUser);
        Gate newGate = gateRepository.findById(historyPostDto.getGateId()).orElseThrow(() -> new EntityNotFoundException(Gate.class, "id", historyPostDto.getGateId().toString()));
        newHistory.setGate(newGate);
        return mapStructMapper.historyToHistoryGetDto(historyRepository.save(newHistory));
    }

    @Override
    public HistoryGetDto getHistoryById(Long historyId) {
        Optional<History> optionalHistory = historyRepository.findById(historyId);
        if (optionalHistory.isPresent()) {
            return mapStructMapper.historyToHistoryGetDto(optionalHistory.get());
        } else {
            throw new EntityNotFoundException(History.class, "id", historyId.toString());
        }
    }

    @Override
    public Page<HistoryGetDto> findAllHistories(int pageNumber, int pageSize, Long gateId, String keyword, Date startDate, Date endDate) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<History> histories = historyRepository.findAllHistories(gateId, null, null, keyword, startDate, endDate);
        List<HistoryGetDto> historyGetDtos = mapStructMapper.historiesToHistoryGetDtos(histories);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), historyGetDtos.size());
        return new PageImpl<>(historyGetDtos.subList(start, end), pageRequest, historyGetDtos.size());
    }

    @Override
    public OverviewStatistic overviewStatistic(int month, int year) {
        int totalResidents = userRepository.findAllResidents(true, null).size();
        int totalGateKeepers = userRepository.findAllGateKeepers(true, null).size();
        Long totalVisitorPerMonth = historyRepository.countVisitorPerMonth(null, month, year, false);
        return new OverviewStatistic(totalResidents, totalGateKeepers, totalVisitorPerMonth);
    }

    @Override
    public List<DayStatistic> monthStatistic(int month, int year) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        List<DayStatistic> monthStatistic = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            String day1 = day + "/" + month;
            Long totalResidents = historyRepository.countVisitorPerMonth(day, month, year, true);
            Long totalVisitors = historyRepository.countVisitorPerMonth(day, month, year, false);
            DayStatistic dayStatistic = new DayStatistic(day1, totalResidents, totalVisitors);
            monthStatistic.add(dayStatistic);
        }
        return monthStatistic;
    }

    @Override
    public List<HistoryGetDto> getAllHistories(Long userId) {
        List<History> histories = historyRepository.findAllHistories(null, userId, null, null, null, null);
        return mapStructMapper.historiesToHistoryGetDtos(histories);
    }

    @Override
    public int countUnreadNotification(Long userId) {
        List<History> histories = historyRepository.findAllHistories(null, userId, false, null, null, null);
        return histories.size();
    }

    @Override
    public Long countEntryExitPerDay(Date date, Long userId) {
        return historyRepository.countEntryExitPerDay(date, userId);
    }

    @Override
    public HistoryGetDto updateHistory(Long id, HistoryPostDto historyPostDto) {
        Optional<History> existingHistory = historyRepository.findById(id);
        if (existingHistory.isPresent()) {
            History oldHistory = existingHistory.get();
            mapStructMapper.updateHistoryFromDTO(historyPostDto, oldHistory);
            return mapStructMapper.historyToHistoryGetDto(historyRepository.save(oldHistory));
        } else {
            throw new EntityNotFoundException(History.class, "id", id.toString());
        }
    }

    @Override
    public void deleteHistory(Long historyId) {
        historyRepository.deleteById(historyId);
    }
}
