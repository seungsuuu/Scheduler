package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Scheduler;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SchedulerResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDate createdAt;

    public SchedulerResponseDto(Scheduler scheduler) {
        this.id = scheduler.getId();
        this.title = scheduler.getTitle();
        this.contents = scheduler.getContents();
        this.username = scheduler.getUsername();
        this.createdAt = scheduler.getCreatedAt();
    }
}
