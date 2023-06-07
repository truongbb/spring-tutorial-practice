package com.github.truongbb.tinytaskmanagement.controller;

import com.github.truongbb.tinytaskmanagement.model.request.TaskRequest;
import com.github.truongbb.tinytaskmanagement.model.response.TaskResponse;
import com.github.truongbb.tinytaskmanagement.service.TaskService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;

    @GetMapping("/")
    public String getTasks(Model model) {
        List<TaskResponse> tasks = taskService.getAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }


    /**
     * APIs
     */

    @GetMapping("/api/v1/tasks/status")
    public ResponseEntity<?> getTaskStatus() {
        return ResponseEntity.ok(taskService.getTaskStatus());
    }

    @PostMapping("/api/v1/tasks")
    public ResponseEntity<?> create(@RequestBody @Valid TaskRequest request) {
        taskService.saveTask(request);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/api/v1/tasks/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getDetail(id));
    }

    @PutMapping("/api/v1/tasks")
    public ResponseEntity<?> update(@RequestBody @Valid TaskRequest request) {
        taskService.saveTask(request);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/api/v1/tasks/{id}/{statusId}")
    public ResponseEntity<?> update(@PathVariable Integer id, @PathVariable String statusId) {
        taskService.updateStatus(id, statusId);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/api/v1/tasks/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        taskService.delete(id);
        return ResponseEntity.ok(null);
    }

}
