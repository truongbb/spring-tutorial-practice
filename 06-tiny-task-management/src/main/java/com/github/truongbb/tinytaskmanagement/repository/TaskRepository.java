package com.github.truongbb.tinytaskmanagement.repository;

import com.github.truongbb.tinytaskmanagement.entity.Task;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRepository {

    static int AUTO_ID = 1;

    final List<Task> tasks = new ArrayList<>();

    public List<Task> getAll() {
        return tasks;
    }

    public void add(Task task) {
        task.setId(AUTO_ID);
        AUTO_ID++;
        tasks.add(task);
    }

    public Task getOne(Integer id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst().orElseGet(null);
    }

    public void update(Task task) {
        tasks.forEach(t -> {
            if (t.getId() != task.getId()) {
                return;
            }
//            t.setName(task.getName());
//            t.setDescription(task.getDescription());
//            t.setStatus(task.getStatus());
//            t.setCreatedDateTime(task.getCreatedDateTime());
//            t.setExpectedEndTime(task.getExpectedEndTime());
//            t.setOverdue(task.getOverdue());

            BeanUtils.copyProperties(task, t);
        });
    }

    public void delete(Integer id) {
        tasks.removeIf(t -> t.getId() == id);
    }
}
