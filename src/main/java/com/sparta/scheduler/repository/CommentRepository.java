package com.sparta.scheduler.repository;

import com.sparta.scheduler.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndScheduler_Id(Long commentId, Long scheduleId);
}
