package org.example.studentexammanagement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchStudentDto {

    // dto - data transfer object

    Long id;
    String name;
    String email;
    String phone;
    Float gpa;
    LocalDate dob;
    String gender;
    Long totalRecord;

}
