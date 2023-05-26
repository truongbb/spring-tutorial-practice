package com.github.truongbb.tinytaskmanagement.repository;

import com.github.truongbb.tinytaskmanagement.entity.Task;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRepository {

    final List<Task> tasks = new ArrayList<>();

    public List<Task> getAll() {
        return tasks;
    }

}
