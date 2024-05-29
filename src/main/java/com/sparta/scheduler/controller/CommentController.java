package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.CommentRequestDto;
import com.sparta.scheduler.dto.CommentResponseDto;
import com.sparta.scheduler.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/schedulers/{schedulerId}/comments")
    public CommentResponseDto createComment(@PathVariable Long schedulerId, @RequestBody CommentRequestDto requestDto) {

        return commentService.createComment(schedulerId, requestDto);
    }

    @GetMapping("/schedulers/{schedulerId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long schedulerId) {

        return commentService.getComments(schedulerId);
    }

    @PutMapping("/schedulers/{schedulerId}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long schedulerId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {

        return commentService.updateComment(schedulerId, commentId, requestDto);
    }

    @DeleteMapping("/schedulers/{schedulerId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long schedulerId, @PathVariable Long commentId, @RequestHeader Long userId) {

        commentService.deleteComment(schedulerId, commentId, userId);
        return new ResponseEntity<>("선택한 댓글이 삭제 완료되었습니다.", HttpStatus.OK);
    }

}
