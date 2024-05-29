package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "user_id", nullable = false) // 임시 열
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "scheduler_id", nullable = false)
    private Scheduler scheduler;

    public Comment(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.userId = requestDto.getUserId();
    }

    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
    }

}
