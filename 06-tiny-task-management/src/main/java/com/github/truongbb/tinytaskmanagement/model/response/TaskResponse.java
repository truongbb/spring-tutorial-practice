package com.github.truongbb.tinytaskmanagement.model.response;

import com.github.truongbb.tinytaskmanagement.statics.TaskStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {

    int id;

    String name;

    String description;

    TaskStatus status;

    LocalDateTime createdDateTime;

    LocalDateTime expectedEndTime;

    Boolean overdue;

}
