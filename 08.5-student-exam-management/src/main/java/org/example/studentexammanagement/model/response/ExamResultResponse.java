package org.example.studentexammanagement.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamResultResponse {

    Long id;

    StudentResponse student;

    SubjectResponse subject;

    Float mark;

    LocalDate examDate;

    Integer examTimes;

}
