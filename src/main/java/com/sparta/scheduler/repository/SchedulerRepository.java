package com.sparta.scheduler.repository;

import com.sparta.scheduler.entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {
    List<Scheduler> findAllByOrderByCreatedAtDesc();
}
