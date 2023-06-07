package com.github.truongbb.tinytaskmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.truongbb.tinytaskmanagement.entity.Task;
import com.github.truongbb.tinytaskmanagement.model.request.TaskRequest;
import com.github.truongbb.tinytaskmanagement.model.response.TaskDetailResponse;
import com.github.truongbb.tinytaskmanagement.model.response.TaskResponse;
import com.github.truongbb.tinytaskmanagement.model.response.TaskStatusResponse;
import com.github.truongbb.tinytaskmanagement.repository.TaskRepository;
import com.github.truongbb.tinytaskmanagement.statics.TaskStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<TaskDetailResponse> tempData = tasks.stream().map(t -> objectMapper.convertValue(t, TaskDetailResponse.class)).collect(Collectors.toList());
        List<TaskStatus> taskStatuses = Arrays.asList(TaskStatus.values());

//        List<TaskResponse> result = new ArrayList<>();
//        for (int i = 0; i < taskStatuses.size(); i++) {
//            TaskStatus taskStatus = taskStatuses.get(i);
//            List<TaskDetailResponse> list = new ArrayList<>();
//
//            for (int j = 0; j < tempData.size(); j++) {
//                if(tempData.get(j).getStatus().equals(taskStatus)) {
//                    list.add(tempData.get(j));
//                }
//            }
//            TaskResponse taskResponse = new TaskResponse(taskStatus, list);
//            result.add(taskResponse);
//        }
//        return result;


        return taskStatuses.stream().map(status -> {
            List<TaskDetailResponse> taskDetailResponses = tempData.stream().filter(t -> t.getStatus().equals(status)).collect(Collectors.toList());
            return new TaskResponse(status, taskDetailResponses);
        }).collect(Collectors.toList());
    }

    public List<TaskStatusResponse> getTaskStatus() {
        return List.of(
                TaskStatusResponse.builder().code(TaskStatus.TODO.getCode()).name(TaskStatus.TODO.getName()).build(),
                TaskStatusResponse.builder().code(TaskStatus.IN_PROGRESS.getCode()).name(TaskStatus.IN_PROGRESS.getName()).build(),
                TaskStatusResponse.builder().code(TaskStatus.REVIEWING.getCode()).name(TaskStatus.REVIEWING.getName()).build(),
                TaskStatusResponse.builder().code(TaskStatus.COMPLETED.getCode()).name(TaskStatus.COMPLETED.getName()).build()
        );
    }

    public void saveTask(TaskRequest request) {
        Task task = objectMapper.convertValue(request, Task.class);
        if (!ObjectUtils.isEmpty(request.getId())) {
            taskRepository.update(task);
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        task.setCreatedDateTime(now);
        task.setOverdue(task.getExpectedEndTime().isBefore(now));
        taskRepository.add(task);
    }

    public TaskDetailResponse getDetail(Integer id) {
        Task task = taskRepository.getOne(id);
        return objectMapper.convertValue(task, TaskDetailResponse.class);
    }

    public void delete(Integer id) {
        taskRepository.delete(id);
    }

    public void updateStatus(Integer id, String statusId) {
        Task task = taskRepository.getOne(id);
        if (ObjectUtils.isEmpty(task)) {
            return;
        }
        task.setStatus(TaskStatus.valueOf(statusId));
        taskRepository.update(task);
    }
}
