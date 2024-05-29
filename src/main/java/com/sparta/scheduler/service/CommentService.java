package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.CommentRequestDto;
import com.sparta.scheduler.dto.CommentResponseDto;
import com.sparta.scheduler.entity.Comment;
import com.sparta.scheduler.entity.Scheduler;
import com.sparta.scheduler.repository.CommentRepository;
import com.sparta.scheduler.repository.SchedulerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SchedulerRepository schedulerRepository;

    public CommentService(CommentRepository commentRepository, SchedulerRepository schedulerRepository) {
        this.commentRepository = commentRepository;
        this.schedulerRepository = schedulerRepository;
    }


    public CommentResponseDto createComment(Long schedulerId, CommentRequestDto requestDto) {

        if (schedulerId == null || schedulerId < 0) {
            throw new IllegalArgumentException("일정 ID가 입력되지 않았습니다.");
        }

        if(requestDto.getContents().isEmpty()){
            throw new IllegalArgumentException("댓글 내용이 입력되지 않았습니다.");
        }

        Scheduler scheduler = schedulerRepository.findById(schedulerId).orElseThrow(
                ()-> new IllegalArgumentException("입력한 일정 ID에 해당하는 일정은 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto);

        comment.setScheduler(scheduler);

        Comment saveComment = commentRepository.save(comment);

        CommentResponseDto responseDto = new CommentResponseDto(saveComment);

        return responseDto;
    }

    public List<CommentResponseDto> getComments(Long schedulerId) {

        if (schedulerId == null || schedulerId < 0) {
            throw new IllegalArgumentException("일정 ID가 입력되지 않았습니다.");
        }

        Scheduler scheduler = schedulerRepository.findById(schedulerId).orElseThrow(
                ()-> new IllegalArgumentException("입력한 일정 ID에 해당하는 일정은 존재하지 않습니다.")
        );

        List<CommentResponseDto> responseDtos = scheduler.getCommentList().stream().map(CommentResponseDto::new).toList();

        return responseDtos;
    }
}
