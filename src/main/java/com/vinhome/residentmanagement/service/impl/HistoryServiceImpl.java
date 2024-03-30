package com.vinhome.residentmanagement.service.impl;

import com.vinhome.residentmanagement.dtos.HistoryGetDto;
import com.vinhome.residentmanagement.dtos.HistoryPostDto;
import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.History;
import com.vinhome.residentmanagement.exception.EntityNotFoundException;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.repository.HistoryRepository;
import com.vinhome.residentmanagement.service.HistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryServiceImpl implements HistoryService {
    private HistoryRepository historyRepository;
    private MapStructMapper mapStructMapper;

    public HistoryServiceImpl(HistoryRepository historyRepository, MapStructMapper mapStructMapper) {
        this.historyRepository = historyRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Override
    public HistoryGetDto createHistory(HistoryPostDto historyPostDto) {
        History newHistory = mapStructMapper.historyPostDtoToHistory(historyPostDto);
        return mapStructMapper.historyToHistoryGetDto(historyRepository.save(newHistory));
    }

    @Override
    public HistoryGetDto getHistoryById(Long historyId) {
        Optional<History> optionalHistory = historyRepository.findById(historyId);
        if(optionalHistory.isPresent()){
            return mapStructMapper.historyToHistoryGetDto(optionalHistory.get());
        }else{
            throw new EntityNotFoundException(History.class, "id", historyId.toString());
        }
    }

    @Override
    public List<HistoryGetDto> getAllHistories() {
        List<History> histories = historyRepository.findAll();
        return mapStructMapper.historiesToHistoryGetDtos(histories);
    }

    @Override
    public HistoryGetDto updateHistory(Long id, HistoryPostDto historyPostDto) {
        Optional<History> existingHistory = historyRepository.findById(id);
        if(existingHistory.isPresent()){
            History oldHistory = existingHistory.get();
            mapStructMapper.updateHistoryFromDTO(historyPostDto, oldHistory);
            return mapStructMapper.historyToHistoryGetDto(historyRepository.save(oldHistory));
        }else {
            throw new EntityNotFoundException(History.class, "id", id.toString());
        }
    }

    @Override
    public void deleteHistory(Long historyId) {
        historyRepository.deleteById(historyId);
    }
}
