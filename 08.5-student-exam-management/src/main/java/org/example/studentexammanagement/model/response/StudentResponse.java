package org.example.studentexammanagement.model.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.studentexammanagement.constant.Gender;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {

    Long id;
    String name;
    String email;
    String phone;
    Float gpa;
    LocalDate dob;
    Gender gender;

}
