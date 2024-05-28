package com.sparta.scheduler.entity;

import com.zaxxer.hikari.metrics.dropwizard.CodahaleMetricsTrackerFactory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coment")
@NoArgsConstructor
public class Coment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "user_id", nullable = false) // 임시 열
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "scheduler_id")
    private Scheduler scheduler;

}
