package com.github.truongbb.tinytaskmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.truongbb.tinytaskmanagement.entity.Task;
import com.github.truongbb.tinytaskmanagement.model.response.TaskResponse;
import com.github.truongbb.tinytaskmanagement.model.response.TaskStatusResponse;
import com.github.truongbb.tinytaskmanagement.repository.TaskRepository;
import com.github.truongbb.tinytaskmanagement.statics.TaskStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {

    ObjectMapper objectMapper;

    TaskRepository taskRepository;

    public List<TaskResponse> getAll() {
        List<Task> tasks = taskRepository.getAll();
        return tasks.stream().map(t -> objectMapper.convertValue(t, TaskResponse.class)).collect(Collectors.toList());
    }

    public List<TaskStatusResponse> getTaskStatus() {
        return List.of(
                TaskStatusResponse.builder().code(TaskStatus.TODO.getCode()).name(TaskStatus.TODO.getName()).build(),
                TaskStatusResponse.builder().code(TaskStatus.IN_PROGRESS.getCode()).name(TaskStatus.IN_PROGRESS.getName()).build(),
                TaskStatusResponse.builder().code(TaskStatus.REVIEWING.getCode()).name(TaskStatus.REVIEWING.getName()).build(),
                TaskStatusResponse.builder().code(TaskStatus.COMPLETED.getCode()).name(TaskStatus.COMPLETED.getName()).build()
        );
    }
}
