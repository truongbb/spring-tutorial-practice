package com.github.truongbb.tinytaskmanagement.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.truongbb.tinytaskmanagement.statics.TaskStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDetailResponse {

    int id;

    String name;

    String description;

    TaskStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime createdDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime expectedEndTime;

    Boolean overdue;

}
