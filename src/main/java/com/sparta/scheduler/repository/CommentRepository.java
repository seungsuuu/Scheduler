package com.sparta.scheduler.repository;

import com.sparta.scheduler.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
