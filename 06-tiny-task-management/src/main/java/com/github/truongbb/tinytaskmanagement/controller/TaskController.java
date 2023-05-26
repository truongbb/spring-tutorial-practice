package com.github.truongbb.tinytaskmanagement.controller;

import com.github.truongbb.tinytaskmanagement.model.response.TaskResponse;
import com.github.truongbb.tinytaskmanagement.service.TaskService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;

    @GetMapping("/tasks")
    public String getTasks(Model model) {
        List<TaskResponse> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "";
    }


    /**
     * APIs
     */

    @GetMapping("/api/v1/tasks/status")
    public ResponseEntity<?> getTaskStatus() {
        return ResponseEntity.ok(taskService.getTaskStatus());
    }

}
