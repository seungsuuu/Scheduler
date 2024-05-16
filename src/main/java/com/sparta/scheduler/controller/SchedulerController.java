package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.SchedulerRequestDto;
import com.sparta.scheduler.dto.SchedulerResponseDto;
import com.sparta.scheduler.service.SchedulerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SchedulerController {
    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/schedulers")
    public SchedulerResponseDto createScheduler(@RequestBody SchedulerRequestDto requestDto) {

        return schedulerService.createScheduler(requestDto);

    }

    @GetMapping("/schedulers/select")
    public SchedulerResponseDto getSchedulersById(@RequestParam Long id) {

        return schedulerService.getSchedulersById(id);

    }

    @GetMapping("/schedulers/all")
    public List<SchedulerResponseDto> getScheduler() {

        return schedulerService.getScheduler();

    }

}
