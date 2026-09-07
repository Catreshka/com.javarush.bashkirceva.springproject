package com.javarush.bashkirceva.springproject.security;

import com.javarush.bashkirceva.springproject.repository.TaskRepository;
import org.springframework.stereotype.Component;

@Component("taskSecurity")
public class TaskSecurity {

    private final TaskRepository taskRepository;

    public TaskSecurity(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean isOwner(Long taskId, String username) {
        return taskRepository.existsByIdAndUserUsername(taskId, username);
    }
}
