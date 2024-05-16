package com.sparta.scheduler.repository;

import com.sparta.scheduler.dto.SchedulerResponseDto;
import com.sparta.scheduler.entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {
    List<Scheduler> findAllByOrderByCreatedAtDesc();
    Optional<Scheduler> findById(Long id);
}
