package org.example.studentexammanagement.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamResultRequest {

    @NotNull
    @Min(value = 1)
    Long studentId;

    @NotNull
    @Min(value = 1)
    Long subjectId;

    @NotNull
    @Range(min = 1, max = 10)
    Float mark;

    @NotNull
    @Past
    LocalDate examDate;

}
