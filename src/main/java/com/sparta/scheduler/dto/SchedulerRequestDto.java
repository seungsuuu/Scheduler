package com.sparta.scheduler.dto;

import lombok.Getter;

@Getter
public class SchedulerRequestDto {
    private String title;
    private String contents;
    private String username;
    private String password;
}
