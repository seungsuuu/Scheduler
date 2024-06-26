package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.CommentRequestDto;
import com.sparta.scheduler.dto.CommentResponseDto;
import com.sparta.scheduler.entity.Comment;
import com.sparta.scheduler.entity.Scheduler;
import com.sparta.scheduler.repository.CommentRepository;
import com.sparta.scheduler.repository.SchedulerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SchedulerRepository schedulerRepository;

    public CommentService(CommentRepository commentRepository, SchedulerRepository schedulerRepository) {
        this.commentRepository = commentRepository;
        this.schedulerRepository = schedulerRepository;
    }


    public CommentResponseDto createComment(Long schedulerId, CommentRequestDto requestDto, Long userId) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId).orElseThrow(
                () -> new IllegalArgumentException("입력한 일정 ID에 해당하는 일정은 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto);
        comment.setUserId(userId);
        scheduler.addCommentList(comment);

        Comment saveComment = commentRepository.save(comment);

        CommentResponseDto responseDto = new CommentResponseDto(saveComment);
        return responseDto;
    }

    public List<CommentResponseDto> getComments(Long schedulerId) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId).orElseThrow(
                () -> new IllegalArgumentException("입력한 일정 ID에 해당하는 일정은 존재하지 않습니다.")
        );

        List<CommentResponseDto> responseDtos = scheduler.getCommentList().stream().map(CommentResponseDto::new).toList();
        return responseDtos;
    }

    @Transactional
    public CommentResponseDto updateComment(Long schedulerId, Long commentId, CommentRequestDto requestDto, Long userId) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId).orElseThrow(
                () -> new IllegalArgumentException("입력한 일정 ID에 해당하는 일정은 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findByIdAndScheduler_Id(commentId, scheduler.getId()).orElseThrow(
                () -> new IllegalArgumentException("입력한 댓글 ID에 해당하는 일정은 존재하지 않습니다.")
        );

        if (comment.getUserId().equals(userId)) {

            comment.update(requestDto);

        } else {
            throw new IllegalArgumentException("작성자 ID가 일치하지 않습니다.");
        }

        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    public void deleteComment(Long schedulerId, Long commentId, Long userId) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId).orElseThrow(
                () -> new IllegalArgumentException("입력한 일정 ID에 해당하는 일정은 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findByIdAndScheduler_Id(commentId, scheduler.getId()).orElseThrow(
                () -> new IllegalArgumentException("입력한 댓글 ID에 해당하는 일정은 존재하지 않습니다.")
        );

        if (comment.getUserId().equals(userId)) {

            commentRepository.delete(comment);

        } else {
            throw new IllegalArgumentException("작성자 ID가 일치하지 않습니다.");
        }
    }
}
