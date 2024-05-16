package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.SchedulerRequestDto;
import com.sparta.scheduler.dto.SchedulerResponseDto;
import com.sparta.scheduler.entity.Scheduler;
import com.sparta.scheduler.repository.SchedulerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerService {

    private final SchedulerRepository schedulerRepository;

    public SchedulerService(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    public SchedulerResponseDto createScheduler(SchedulerRequestDto requestDto) {
        // RequestDto -> Entity
        Scheduler scheduler = new Scheduler(requestDto);

        // DB 저장
        Scheduler saveScheduler = schedulerRepository.save(scheduler);

        // Entity -> ResponseDto
        SchedulerResponseDto ResponseDto = new SchedulerResponseDto(saveScheduler);

        return ResponseDto;
    }

    public SchedulerResponseDto getSchedulersById(Long id) {
        Scheduler scheduler = schedulerRepository.findById(id).orElseThrow(NullPointerException::new);
        return new SchedulerResponseDto(scheduler);
    }

    public List<SchedulerResponseDto> getScheduler() {
        // DB 조회
        return schedulerRepository.findAllByOrderByCreatedAtDesc().stream().map(SchedulerResponseDto::new).toList();

    }
}
