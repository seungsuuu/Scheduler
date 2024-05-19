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
    public SchedulerResponseDto getSchedulerById(@RequestParam Long id) {

        return schedulerService.getSchedulerById(id);

    }

    @GetMapping("/schedulers/all")
    public List<SchedulerResponseDto> getSchedulers() {

        return schedulerService.getSchedulers();

    }

    @PutMapping("/schedulers/{id}")
    public SchedulerResponseDto updateScheduler(@PathVariable Long id, @RequestBody SchedulerRequestDto requestDto) {

        return schedulerService.updateScheduler(id, requestDto);

    }

    @DeleteMapping("/schedulers/{id}")
    public Long deleteScheduler(@PathVariable Long id, @RequestHeader("password") String password) {

        return schedulerService.deleteScheduler(id, password);

    }

}
