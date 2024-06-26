package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.CommentRequestDto;
import com.sparta.scheduler.dto.CommentResponseDto;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    public CommentResponseDto createComment(@Valid @NotBlank @PathVariable Long schedulerId,
                                            @Valid @RequestBody CommentRequestDto requestDto,
                                            HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");
        return commentService.createComment(schedulerId, requestDto, user.getId());
    }

    @GetMapping("/schedulers/{schedulerId}/comments")
    public List<CommentResponseDto> getComments(@Valid @NotBlank @PathVariable Long schedulerId) {

        return commentService.getComments(schedulerId);
    }

    @PutMapping("/schedulers/{schedulerId}/comments/{commentId}")
    public CommentResponseDto updateComment(@Valid @NotBlank @PathVariable Long schedulerId,
                                            @Valid @NotBlank @PathVariable Long commentId,
                                            @Valid @RequestBody CommentRequestDto requestDto,
                                            HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");
        return commentService.updateComment(schedulerId, commentId, requestDto, user.getId());
    }

    @DeleteMapping("/schedulers/{schedulerId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@Valid @NotBlank @PathVariable Long schedulerId,
                                                @Valid @NotBlank @PathVariable Long commentId,
                                                HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");
        commentService.deleteComment(schedulerId, commentId, user.getId());
        return new ResponseEntity<>("선택한 댓글이 삭제 완료되었습니다.", HttpStatus.valueOf(200));
    }
}
