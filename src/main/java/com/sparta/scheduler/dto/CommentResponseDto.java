package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String contents;
    private Long userId;
    private Long schedulerId;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.userId = comment.getUserId();
        this.schedulerId = comment.getScheduler().getId();
        this.createdAt = comment.getCreatedAt();
    }
}
