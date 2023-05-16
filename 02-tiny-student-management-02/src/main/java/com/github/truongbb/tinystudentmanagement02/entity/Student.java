package com.github.truongbb.tinystudentmanagement02.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    int id;

    String name;

//    String email;

    String address;

    String phone;

    LocalDate dob;

    float gpa;


}
