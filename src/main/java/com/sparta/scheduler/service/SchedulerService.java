package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.SchedulerRequestDto;
import com.sparta.scheduler.dto.SchedulerResponseDto;
import com.sparta.scheduler.entity.Scheduler;
import com.sparta.scheduler.repository.SchedulerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public SchedulerResponseDto getSchedulerById(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Scheduler scheduler = findScheduler(id);

        return new SchedulerResponseDto(scheduler);
    }

    public List<SchedulerResponseDto> getSchedulers() {
        // DB 조회
        return schedulerRepository.findAllByOrderByCreatedAtDesc().stream().map(SchedulerResponseDto::new).toList();
    }

    @Transactional
    public SchedulerResponseDto updateScheduler(Long id, SchedulerRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Scheduler scheduler = findScheduler(id);

        // 비밀번호 일치 확인
        if (scheduler.getPassword().equals(requestDto.getPassword())) {
            // memo 내용 수정
            scheduler.update(requestDto);

        } else {
            throw new IllegalArgumentException("수정 요청한 일정의 비밀번호가 일치하지 않습니다.");
        }

        return new SchedulerResponseDto(scheduler);
    }

    public Long deleteScheduler(Long id, String password) {
        // 해당 메모가 DB에 존재하는지 확인
        Scheduler scheduler = findScheduler(id);

        // 비밀번호 일치 확인
        if (password.equals(scheduler.getPassword())) {
            // memo 삭제
            schedulerRepository.delete(scheduler);

        } else {
            throw new IllegalArgumentException("삭제 요청한 일정의 비밀번호가 일치하지 않습니다.");
        }

        return id;
    }

    private Scheduler findScheduler(Long id) {
        return schedulerRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));
    }

}
