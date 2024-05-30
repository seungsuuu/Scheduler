package com.sparta.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "댓글 내용을 작성해주세요.")
    private String contents;

    @NotNull
    private Long userId;
}
