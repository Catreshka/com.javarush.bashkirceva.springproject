package com.javarush.bashkirceva.springproject.repository;

import com.javarush.bashkirceva.springproject.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByIdAndUserUsername(Long id, String username);

    List<Task> findByUserId(Long userId);

    List<Task> findByStatus(String status);

    List<Task> findByDeadlineBefore(LocalDate deadline);

    List<Task> findByDeadlineBetween(LocalDate from, LocalDate to);
}
