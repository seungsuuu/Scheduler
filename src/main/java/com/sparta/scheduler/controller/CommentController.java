package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.CommentRequestDto;
import com.sparta.scheduler.dto.CommentResponseDto;
import com.sparta.scheduler.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments/{schedulerId}")
    public CommentResponseDto createComment(@PathVariable Long schedulerId, @RequestBody CommentRequestDto requestDto) {

        return commentService.createComment(schedulerId ,requestDto);

    }

    @GetMapping("/comments/all/{schedulerId}")
    public List<CommentResponseDto> getComments(@PathVariable Long schedulerId) {

        return commentService.getComments(schedulerId);

    }


}
