package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.SchedulerRequestDto;
import com.sparta.scheduler.dto.SchedulerResponseDto;
import com.sparta.scheduler.service.SchedulerService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/schedulers")
    public SchedulerResponseDto getSchedulersByTitle(@RequestParam Long id) {

        return schedulerService.getSchedulersByTitle(id);

    }

}
